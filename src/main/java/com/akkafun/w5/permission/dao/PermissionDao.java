package com.akkafun.w5.permission.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.akkafun.w5.permission.model.Permission;
import com.akkafun.platform.genericdao.dao.hibernate.GenericDAOImpl;
import com.akkafun.platform.genericdao.search.Filter;
import com.akkafun.platform.genericdao.search.Search;

@Repository
public class PermissionDao extends GenericDAOImpl<Permission, Long> {

	public void deleteByOperation(Long operationId) {
		String hql = "delete from Permission p where p.operationId = ?)";
		super.update(hql, operationId);
	}

	public List<Permission> findByOperationName(Collection<String> operationNames) {
		Search search = new Search();
		search.addFilter(Filter.in("operation.name", operationNames));
		return super.search(search);
	}

}
