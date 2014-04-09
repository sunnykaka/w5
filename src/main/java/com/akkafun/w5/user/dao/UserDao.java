package com.akkafun.w5.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.akkafun.w5.user.model.User;
import com.akkafun.platform.genericdao.dao.hibernate.GenericDAOImpl;
import com.akkafun.platform.genericdao.search.Filter;
import com.akkafun.platform.genericdao.search.Search;

@Repository
public class UserDao extends GenericDAOImpl<User, Long> {

	public List<User> findByRole(Long roleId) {
		Search search = new Search();
		search.addFilter(Filter.in("roleId", roleId));
		return super.search(search);
	}
	
}
