package com.akkafun.w5.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.akkafun.w5.common.web.WebHolder;

/**
 * 
 * @author liubin
 * @date 2012-11-20
 *
 */
public class WebHolderInterceptor extends HandlerInterceptorAdapter {
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		WebHolder.set(request);
		return true;
	}


	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		WebHolder.remove();
	}


}
