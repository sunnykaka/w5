package com.akkafun.w5.permission.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import com.akkafun.w5.user.model.UserStatus;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Sets;
import com.akkafun.w5.common.Constants;
import com.akkafun.w5.common.web.WebHolder;
import com.akkafun.w5.permission.dao.OperationDao;
import com.akkafun.w5.permission.dao.PermissionDao;
import com.akkafun.w5.permission.dao.ResourceDao;
import com.akkafun.w5.permission.dao.RoleDao;
import com.akkafun.w5.permission.dao.RolePermissionDao;
import com.akkafun.w5.permission.jaxb.Permissions;
import com.akkafun.w5.permission.model.Operation;
import com.akkafun.w5.permission.model.Permission;
import com.akkafun.w5.permission.model.Resource;
import com.akkafun.w5.permission.model.Role;
import com.akkafun.w5.permission.model.RolePermission;
import com.akkafun.w5.user.model.User;
import com.akkafun.w5.user.service.UserService;
import com.akkafun.platform.exception.AppException;
import com.akkafun.platform.exception.BusinessException;
import com.akkafun.platform.id.IdGenerator;
import com.akkafun.platform.util.MD5Encoder;

@Service
public class PermissionService {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OperationDao operationDao;
	
	@Autowired
	private PermissionDao permissionDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private RolePermissionDao rolePermissionDao;
	
	@Autowired
	private IdGenerator idGenerator;

	/**
	 * 根据permissions.xml导入权限
	 * @param permissions
	 * @param updateRoles 
	 */
	@Transactional
	public void refreshPermissions(Permissions permissions, boolean updateRoles) {
		Permissions.User pUser = permissions.getUser();
		User user = userService.getByUsername(pUser.getUsername());
		if(user == null) {
			
			Role role = roleService.getByName(Constants.ADMINISTRATOR_ROLE_NAME);
			if(role == null) {
				role = new Role();
				role.setName(Constants.ADMINISTRATOR_ROLE_NAME);
				role.setAdmin(true);
				WebHolder.fillOperatorValues(role);
				role.setUpdatable(false);
				roleService.save(role);
			}
			
			user = new User();
			user.setUsername(pUser.getUsername());
			user.setName(pUser.getName());
			WebHolder.fillOperatorValues(user);
			String password = "111111";
			user.setPassword(MD5Encoder.encode(password));
			user.setType(null);
			user.setRoleId(role.getId());
            user.setStatus(UserStatus.NORMAL);
			userService.save(user);
			
		}
		
		//1.首先遍历所有resource与operation,检验配置是否有效
		//1)所有resource与operation的name必须唯一,不能重复
		//2)operation所有required的name必须存在
		//2.比对数据库中的resource列表与现在文件中的resource列表,查找哪些resource已经在新文件中被删除,从数据库中删除这些resource
		//3.处理所有的resource,查找哪些resource下的operation已经在新文件中被删除,从数据库中删除这些operation
		//4.逐条处理resource
		//5.逐条处理operation,首先递归处理required中的operation.然后判断该operation是新增还是修改.
		//1)如果是新增,直接数据库新增operation就好
		//2)如果是修改,找到之前数据库operation的required列表,比对required中的哪些operation需要删除和新增.
		//	忽略删除的,处理新增的.查询哪些角色拥有该operation的权限,将对应角色与新增的operation记录到一个map中,key为角色id,value为要赋予的权限列表
		//6.最后,根据map为角色赋予新的权限.
		//
		List<Permissions.Resource> pResources = permissions.getResource();
		//key为name,value为Permissions.Resource对象
		Map<String, Permissions.Resource> pResourceMap = new LinkedHashMap<>();
		//key为name,value为Permissions.Resource.Operation对象
		Map<String, Permissions.Resource.Operation> pOperationNameMap = new LinkedHashMap<>();
		//key为name,value为Resource对象
		Map<String, Resource> resourceMap = new LinkedHashMap<>();
		//key为name,value为Operation对象
		Map<String, Operation> operationMap = new LinkedHashMap<>();
		//key为operation的name,value为resource对象的id
		Map<String, Long> operationNameResourceIdMap = new HashMap<>();
		//key为role对象的id,value为需要为该role新增operation的name
		Map<Long, Set<String>> permissionToRoleMap = new HashMap<>();
		
		//检验所有resource与operation的name必须唯一,不能重复
		for(Permissions.Resource pResource : pResources) {
			assertNotExist(pResourceMap, pResource.getName(), "resource的name");
			pResourceMap.put(pResource.getName(), pResource);
			List<Permissions.Resource.Operation> pOperations = pResource.getOperation();
			if(pOperations.isEmpty()) {
				throw new BusinessException(String.format("resource[%s] 的operation为空:", pResource.getName()));
			}
			for(Permissions.Resource.Operation pOperation : pOperations) {
				assertNotExist(pOperationNameMap, pOperation.getName(), "operation的name");
				pOperationNameMap.put(pOperation.getName(), pOperation);
			}
		}
		
		//检验operation所有required的name必须存在
		for(Map.Entry<String, Permissions.Resource.Operation> entry : pOperationNameMap.entrySet()) {
			Permissions.Resource.Operation pOperation = entry.getValue();
			if(!StringUtils.isBlank(pOperation.getRequired())) {
				String[] requiredNames = pOperation.getRequired().split(",");
				for(String requiredName : requiredNames) {
					if(!pOperationNameMap.containsKey(requiredName)) {
						throw new BusinessException(String.format("operation[%s]required的[%s]不存在", pOperation.getName(), requiredName));
					}
				}
			}
		}
		
		//比对数据库中的resource列表与现在文件中的resource列表,查找哪些resource已经在新文件中被删除,从数据库中删除这些resource
		List<Resource> resources = resourceDao.findAll();
		for(Resource resource : resources) {
			if(!pResourceMap.containsKey(resource.getName())) {
				log.info("从数据库中删除resource[{}]", resource.getName());
				deleteResource(resource);
			} else {
				resourceMap.put(resource.getName(), resource);
			}
		}
		
		//查找哪些resource下的operation已经在新文件中被删除,从数据库中删除这些operation
		List<Operation> operations = operationDao.findAll();
		for(Operation operation : operations) {
			if(!pOperationNameMap.containsKey(operation.getName())) {
				log.info("从数据库中删除operation[{}]", operation.getName());
				deleteOperation(operation);
			}
		}
		
		//逐条处理resource
		for(Permissions.Resource pResource : pResources) {
			Resource resource = resourceMap.get(pResource.getName());
			if(resource == null) {
				//新增resource
				resource = new Resource();
				resourceMap.put(pResource.getName(), resource);
				resource.setName(pResource.getName());
			}
			resource.setUniqueKey(pResource.getUniqueKey());
			resource.setSystemInit(false);
			resourceDao.save(resource);
			
			List<Permissions.Resource.Operation> pOperations = pResource.getOperation();
			for(Permissions.Resource.Operation pOperation : pOperations) {
				operationNameResourceIdMap.put(pOperation.getName(), resource.getId());
			}
		}
		
		//逐条处理operation,首先递归处理required中的operation.然后判断该operation是新增还是修改.
		//1)如果是新增,直接数据库新增operation就好
		//2)如果是修改,找到之前数据库operation的required列表,比对required中的哪些operation需要删除和新增.
		//	忽略删除的,处理新增的.查询哪些角色拥有该operation的权限,将对应角色与新增的operation记录到一个map中,key为角色id,value为要赋予的权限列表
		operations = operationDao.findAll();
		
		for(Operation operation : operations) {
			operationMap.put(operation.getName(), operation);
		}
		
		POperationHandler pOperationHandler = new POperationHandler(operationMap, pOperationNameMap, 
				 operationNameResourceIdMap, permissionToRoleMap);
		
		for(Map.Entry<String, Permissions.Resource.Operation> entry : pOperationNameMap.entrySet()) {
			Permissions.Resource.Operation pOperation = entry.getValue();
			pOperationHandler.handlePOperation(pOperation);
		}
		
		if(updateRoles) {
			//更新角色与角色所拥有的权限
			//1.首先校验配置是否正确
			//1)role的name是否有重复
			//2)operation引用的name是否都存在,并且是否都为configable=true
			//2.比对数据库中的role,查看哪些role需要删除(需要排除管理员角色,如果待删除的role有用户已经选择,则报异常),删除这些role以及对应的权限
			//3.根据role引用的operation,递归得到role拥有的所有operation,将对应角色与operation记录到一个map中,key为角色name,value为要赋予的权限列表
			//4.删除所有的权限
			//5.将所有权限设置到之前的permissionToRoleMap中
			
			Map<String, Permissions.Role> pRoleMap = new HashMap<>();
			Map<String, Role> roleMap = new HashMap<>();
			//key为role的name,value为要授予role权限的operation name,这个map的权限最后与前面的permissionToRoleMap合并
			Map<String, Set<String>> permissionToRoleNameMap = new HashMap<>();
			
			List<Permissions.Role> pRoles = permissions.getRole();
			for(Permissions.Role pRole : pRoles) {
				//检验role的name是否有重复
				assertNotExist(pRoleMap, pRole.getName(), "role的name");
				pRoleMap.put(pRole.getName(), pRole);
				List<Permissions.Role.Operation> rOperations = pRole.getOperation();
				//该角色的权限集合
				Set<String> permissionSet = new HashSet<>();
				for(Permissions.Role.Operation rOperation : rOperations) {
					//得到引用的operation
					Permissions.Resource.Operation pOperation = pOperationNameMap.get(rOperation.getRef());
					//校验operation引用的name是否都存在,并且是否都为configable=true
					if("false".equalsIgnoreCase(pOperation.getConfigable())) {
						throw new RuntimeException(String.format("role[%s]引用了configable=false的operation[%s]", pRole.getName(), pOperation.getName()));
					}
					//递归得到该operation所有required的权限
					NavigableSet<String> ops = new TreeSet<>();
					ops.add(pOperation.getName());
					while(!ops.isEmpty()) {
						String operationName = ops.pollFirst();
						permissionSet.add(operationName);
						pOperation = pOperationNameMap.get(operationName);
						if(!StringUtils.isBlank(pOperation.getRequired())) {
							String[] requiredOperations = pOperation.getRequired().split(",");
							for(String requiredOperation : requiredOperations) {
								ops.add(requiredOperation);
							}
						}
					}
				}
				permissionToRoleNameMap.put(pRole.getName(), permissionSet);
			}
			//此时已经得到角色拥有的所有权限,所以可以清除原来因为operation新增required所应该赋给角色的权限
			permissionToRoleMap.clear();
			
			//比对数据库中的role,查看哪些role需要删除(需要排除管理员角色,如果待删除的role有用户已经选择,则报异常),删除这些role以及对应的权限
			List<Role> roles = roleService.findAll();
			for(Role role : roles) {
				if(Constants.ADMINISTRATOR_ROLE_NAME.equals(role.getName())) continue;
				if(!pRoleMap.containsKey(role.getName())) {
					log.info("从数据库中删除role[{}]", role.getName());
					try {
						roleService.deleteRole(role);
					} catch (AppException e) {
						throw new RuntimeException(e);
					}
				} else {
					roleMap.put(role.getName(), role);
				}
			}
			
			//逐条处理role
			for(Map.Entry<String, Permissions.Role> entry : pRoleMap.entrySet()) {
				String roleName = entry.getKey();
				Role role = roleMap.get(roleName);
				if(role == null) {
					role = new Role();
					role.setName(roleName);
					WebHolder.fillOperatorValues(role);
					role.setAdmin(false);
					role.setUpdatable(true);
					roleService.save(role);
				}
				//此时将permissionToRoleNameMap里的权限设置到permissionToRoleMap,方便最后统一处理
				Set<String> permissionToRoleOperations = permissionToRoleNameMap.get(roleName);
				if(permissionToRoleOperations != null && !permissionToRoleOperations.isEmpty()) {
					permissionToRoleMap.put(role.getId(), permissionToRoleOperations);
				}
			}
			
			//删除所有的权限
			deleteAllRolePermissions();
		}
		
		//最后,根据map为角色赋予新的权限.
		for(Map.Entry<Long, Set<String>> entry : permissionToRoleMap.entrySet()) {
			Long roleId = entry.getKey();
			Set<String> operationNames = entry.getValue();
			if(operationNames != null && !operationNames.isEmpty()) {
				grantPermissions(roleId, operationNames);
			}
		}
	}
	
	@Transactional
	public void deleteAllRolePermissions() {
		rolePermissionDao.deleteAll();
	}
	
	private void assertNotExist(Map<String, ?> map, String key, String label) {
		if(map.containsKey(key)) {
			throw new BusinessException(String.format(label + "重复,key:[%s]", key));
		}
	}
	
	@Transactional
	public void deleteResource(Resource resource) {
		List<Operation> operations = operationDao.findByResource(resource.getId());
		for(Operation operation : operations) {
			deleteOperation(operation);
		}
		resourceDao.delete(resource);
	}
	
	@Transactional
	public void deleteOperation(Operation operation) {
		rolePermissionDao.deleteByOperation(operation.getId());
		permissionDao.deleteByOperation(operation.getId());
		operationDao.delete(operation);
	}
	
	@Transactional(readOnly = true)
	public List<Role> findRoleByOperation(Long operationId) {
		return roleDao.findRoleByOperation(operationId);
	}
	
	@Transactional
	public void grantPermissions(Long roleId, Collection<String> operationNames) {
		List<RolePermission> rolePermissions = rolePermissionDao.findByRole(roleId);
		List<Permission> permissions = permissionDao.findByOperationName(operationNames);
		Set<Long> ownedPermissions = new HashSet<>();
		for(RolePermission rp : rolePermissions) {
			ownedPermissions.add(rp.getPermissionId());
		}
		for(Permission p : permissions) {
			if(!ownedPermissions.contains(p.getId())) {
				RolePermission rp = new RolePermission();
				rp.setPermissionId(p.getId());
				rp.setRoleId(roleId);
				rolePermissionDao.save(rp);
			}
		}
	}
	
	private class POperationHandler {
		Map<String, Operation> operationMap;
		Map<String, Permissions.Resource.Operation> pOperationNameMap;
		Map<String, Long> operationNameResourceIdMap;
		Map<Long, Set<String>> permissionToRoleMap;
		//集合中存放着已经处理过的operation的name,防止无限递归
		Set<String> handledPOperationNameSet = new HashSet<>();
		//key为operation的name,value为对应operation的required属性中新增的operation name
		Map<String, Set<String>> operationNewlyRequiredMap = new HashMap<>();
		
		public POperationHandler(
				Map<String, Operation> operationMap,
				Map<String, Permissions.Resource.Operation> pOperationNameMap,
				Map<String, Long> operationNameResourceIdMap, 
				Map<Long, Set<String>> permissionToRoleMap) {
			super();
			this.operationMap = operationMap;
			this.pOperationNameMap = pOperationNameMap;
			this.operationNameResourceIdMap = operationNameResourceIdMap;
			this.permissionToRoleMap = permissionToRoleMap;
		}
		
		private void handlePOperation(Permissions.Resource.Operation pOperation) {
			if(handledPOperationNameSet.contains(pOperation.getName())) {
				//已经处理过了
				return;
			}
			if(!StringUtils.isBlank(pOperation.getRequired())) {
				String[] requiredNames = pOperation.getRequired().split(",");
				for(String requiredName : requiredNames) {
					//递归处理required
					handlePOperation(pOperationNameMap.get(requiredName));
				}
			}
			Operation operation = operationMap.get(pOperation.getName());
			Long resourceId = operationNameResourceIdMap.get(pOperation.getName());
			boolean isNew = operation == null;
			if(isNew) {
				//新增operation
				operation = new Operation();
				operation.setName(pOperation.getName());
				operationMap.put(pOperation.getName(), operation);
			} else {
				//修改operation
				String newlyRequired = pOperation.getRequired();
				String formerlyRequired = operation.getRequired();
				if(!StringUtils.isBlank(newlyRequired)) {
					//新增的required权限
					Set<String> newlyRequiredNames = Sets.newHashSet(newlyRequired.split(","));
					//之前的required权限
					Set<String> formerlyRequiredNames = null;
					if(!StringUtils.isBlank(formerlyRequired)) {
						formerlyRequiredNames = Sets.newHashSet(formerlyRequired.split(","));
					}
					if(formerlyRequiredNames != null && !formerlyRequiredNames.isEmpty()) {
						for(Iterator<String> iter = newlyRequiredNames.iterator(); iter.hasNext();) {
							String newlyRequiredName = iter.next();
							if(formerlyRequiredNames.contains(newlyRequiredName)) {
								iter.remove();
							}
						}
					}
					if(!newlyRequiredNames.isEmpty()) {
						operationNewlyRequiredMap.put(operation.getName(), newlyRequiredNames);
					}
					//得到当前operation的required(递归搜索)的新增权限集合
					Set<String> newlyPermissionToRoleOperations = getAllNewlyOperation(pOperation);
					
					//查询哪些role拥有当前operation权限
					if(!newlyPermissionToRoleOperations.isEmpty()) {
						List<Role> roles = findRoleByOperation(operation.getId());
						if(!roles.isEmpty()) {
							for(Role role : roles) {
								Set<String> permissionToRoleOperations = permissionToRoleMap.get(role.getId());
								if(permissionToRoleOperations == null) {
									permissionToRoleOperations = new HashSet<>();
									permissionToRoleMap.put(role.getId(), permissionToRoleOperations);
								}
								permissionToRoleOperations.addAll(newlyPermissionToRoleOperations);
							}
						}
					}
				}
			}
			operation.setRequired(pOperation.getRequired());
			operation.setUrl(pOperation.getUrl());
			operation.setResourceId(resourceId);
			operation.setConfigable(!"false".equalsIgnoreCase(pOperation.getConfigable()));
			operationDao.save(operation);
			
			if(isNew) {
				Permission p = new Permission();
				p.setOperationId(operation.getId());
				p.setResourceId(resourceId);
				permissionDao.save(p);
			}
			
			//记录到已处理列表
			handledPOperationNameSet.add(pOperation.getName());
		}
		
		private Set<String> getAllNewlyOperation(Permissions.Resource.Operation pOperation) {
			Set<String> results = new HashSet<>();
			Set<String> newlyRequiredOperation = operationNewlyRequiredMap.get(pOperation.getName());
			if(newlyRequiredOperation != null) {
				results.addAll(newlyRequiredOperation);
			}
			if(!StringUtils.isBlank(pOperation.getRequired())) {
				String[] requiredNames = pOperation.getRequired().split(",");
				for(String requiredName : requiredNames) {
					results.addAll(getAllNewlyOperation(pOperationNameMap.get(requiredName)));
				}
			}
			return results;
		}
	}

}
