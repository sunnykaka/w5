package com.akkafun.w5.common.web;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.akkafun.w5.common.model.BusinessData;
import com.akkafun.w5.common.util.OperatorUtil;
import com.akkafun.w5.user.model.User;
import com.akkafun.platform.common.dao.Entity;
import com.akkafun.platform.util.EntityUtil;

/**
 * 在ThreadLocal变量里保存Web的HttpServletRequest对象
 * @author liubin
 *
 */
public class WebHolder {
	
	private static final ThreadLocal<HttpServletRequest> webHolder = new ThreadLocal<>();
	
	public static void set(HttpServletRequest request) {
		webHolder.set(request);
	}
	
	public static void remove() {
		webHolder.remove();
	}
	
	/**
	 * 得到HttpServletRequest
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest)webHolder.get();
	}
	
	/**
	 * 得到HttpSession
	 * @return
	 */
	public static HttpSession getSession() {
		HttpServletRequest request = getRequest();
		if(request == null) return null;
		return request.getSession(false);
	}
	
	/**
	 * 得到登陆用户
	 * @return
	 */
	public static User getUser() {
		HttpSession session = getSession();
		if(session == null) return null;
		return (User)session.getAttribute(User.SESSION_KEY);
	}
	
	

	/**
	 * 向Operator子类中设置必要的属性
	 * @param businessData
	 * @param isCreate 是创建还是更新,true是创建,false是更新
	 */
	public static void fillOperatorValues(BusinessData businessData){
		User user = getUser();
		if(EntityUtil.isCreate((Entity<? extends Serializable>)businessData)) {
			OperatorUtil.createOperator(businessData, user);
		} else {
			OperatorUtil.updateOperator(businessData, user);
		}
	}
}