package com.akkafun.w5.permission.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akkafun.w5.permission.dao.RoleDao;
import com.akkafun.w5.permission.dao.RolePermissionDao;
import com.akkafun.w5.permission.model.Role;
import com.akkafun.w5.user.model.User;
import com.akkafun.w5.user.service.UserService;
import com.akkafun.platform.exception.AppException;
import com.akkafun.platform.genericdao.search.Filter;
import com.akkafun.platform.genericdao.search.Search;

@Service
public class RoleService {
	
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private RolePermissionDao rolePermissionDao;
	
	@Autowired
	private UserService userService;

	@Transactional(readOnly = true)
	public Role getByName(String name) {
		return roleDao.searchUnique(new Search().addFilter(Filter.equal("name", name)));
	}

	@Transactional
	public void save(Role role) {
		roleDao.save(role);
	}

	@Transactional(readOnly = true)
	public List<Role> findAll() {
		return roleDao.findAll();
	}

	@Transactional
	public void deleteRole(Role role) throws AppException {
		List<User> users = userService.findByRole(role.getId());
		if(!users.isEmpty()) {
			throw new AppException(String.format("role[%s]下有%d个用户,不能删除", role.getName(), users.size()));
		}
		rolePermissionDao.deleteByRole(role.getId());
		roleDao.delete(role);
	}

	@Transactional(readOnly = true)
	public Role getCoachRole() {
		return getByName("陪练");
	}
	
	@Transactional(readOnly = true)
	public Role getCustomerRole() {
		return getByName("顾客");
	}

}
