package com.akkafun.w5.permission.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.akkafun.w5.permission.model.Operation;
import com.akkafun.platform.genericdao.dao.hibernate.GenericDAOImpl;
import com.akkafun.platform.genericdao.search.Filter;
import com.akkafun.platform.genericdao.search.Search;

@Repository
public class OperationDao extends GenericDAOImpl<Operation, Long> {

	public List<Operation> findByResource(Long resourceId) {
		Search search = new Search();
		search.addFilter(Filter.equal("resourceId", resourceId));
		return super.search(search);
	}

}
