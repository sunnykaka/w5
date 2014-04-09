package com.akkafun.w5.payslip.dao;

import com.akkafun.platform.genericdao.dao.hibernate.GenericDAOImpl;
import com.akkafun.w5.order.model.OrderBase;
import com.akkafun.w5.payslip.model.Payslip;
import org.springframework.stereotype.Repository;

@Repository
public class PayslipDao extends GenericDAOImpl<Payslip, Long> {


}
