package com.akkafun.w5.order.dao;

import com.akkafun.platform.genericdao.dao.hibernate.GenericDAOImpl;
import com.akkafun.w5.order.model.OrderBase;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao extends GenericDAOImpl<OrderBase, Long> {


}
