package com.akkafun.w5.permission.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.akkafun.w5.permission.model.Role;
import com.akkafun.platform.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class RoleDao extends GenericDAOImpl<Role, Long> {

	public List<Role> findRoleByOperation(Long operationId) {
		String hql = "select distinct r from Role r left join r.permissions p where p.operationId = ?";
		return super.query(hql, null, operationId);
	}

}
