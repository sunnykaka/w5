package com.akkafun.w5.code.dao;

import org.springframework.stereotype.Repository;

import com.akkafun.w5.code.model.Code;
import com.akkafun.platform.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class CodeDao extends GenericDAOImpl<Code, Long> {

}
