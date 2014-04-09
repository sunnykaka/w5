package com.akkafun.w5.permission.dao;

import org.springframework.stereotype.Repository;

import com.akkafun.w5.permission.model.Resource;
import com.akkafun.platform.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class ResourceDao extends GenericDAOImpl<Resource, Long> {

}
