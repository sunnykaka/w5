package com.akkafun.w5.common.interceptor;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.akkafun.w5.permission.model.Permission;
import com.akkafun.w5.permission.util.PermissionUtil;
import com.akkafun.w5.user.model.User;
import com.akkafun.w5.user.service.UserService;
import com.akkafun.platform.common.web.session.SessionProvider;

/**
 * 
 * @author liubin
 * @date 2012-11-20
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	private List<String> exceptUrls = null;
	
	private List<String> permissionExceptUrls = null;
	
	private AntPathMatcher matcher = new AntPathMatcher();
	
	public static final String FROM_URL = "FROM_URL";
	
	@Autowired
	private SessionProvider sessionProvider;
	
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String base = request.getContextPath();
		String uri = request.getRequestURI().substring(base.length());
		
		User user = (User)sessionProvider.getAttr(request, User.SESSION_KEY);
    	if(user == null) {
    		//尝试用cookie登录
    		String loginByCookieTried = (String)sessionProvider.getAttr(request, "loginByCookieTried");
    		if(loginByCookieTried == null || !loginByCookieTried.equals("true")) {
    			user = userService.loginByCookie(request);
    			if(user != null) {
    				sessionProvider.setAttr(request, response, User.SESSION_KEY, user);
    			} else {
    				sessionProvider.setAttr(request, response, "loginByCookieTried", "true");
    			}
    		}
    	}
		
    	boolean checkLogin = true;
    	boolean checkPermission = true;
		if(exceptUrls != null) {
			for(String exceptUrl : exceptUrls) {
				if(matcher.match(exceptUrl, uri)) {
					checkLogin = false;
					checkPermission = false;
					break;
				}
			}
		}
		if(checkLogin) {
			//当前url是否需要校验权限
			if(permissionExceptUrls != null) {
				for(String exceptUrl : permissionExceptUrls) {
					if(matcher.match(exceptUrl, uri)) {
						checkPermission = false;
						break;
					}
				}
			}
		}
		if(checkLogin) {
			if(user == null) {
				StringBuffer url = new StringBuffer(uri);
				String key = null;
				String[] values = null;
				Map params = request.getParameterMap();
				if (params != null && !params.isEmpty()) {
					url.append("?");
					for(Iterator<Map.Entry> iter = params.entrySet().iterator(); iter.hasNext();){
						Map.Entry entry = iter.next();
						key = (String) entry.getKey();
						values = (String[]) entry.getValue();
						for(int i = 0; i < values.length; i++){
							url.append(key);
							url.append("=");
							try {
								url.append(java.net.URLEncoder.encode(values[i], "UTF-8"));
							} catch (UnsupportedEncodingException e) {
								log.warn(e.getMessage(), e);
							}
							url.append("&");
						}
					}
					url.deleteCharAt(url.length() - 1);
				}
				
				log.info("完整URL:" + url);
				sessionProvider.setAttr(request, (HttpServletResponse)response, FROM_URL, url.toString());
				
				response.sendRedirect(base + "/login.action");
				return false;
			} else if(checkPermission){
				//检查权限
				boolean isPermitted = PermissionUtil.isPermitted(user, uri);
				if(!isPermitted) {
					//跳转到无授权页面
					response.sendRedirect(base + "/error/no_permission.action");
					return false;
				}
				return true;
			}
			
		}
		
		return true;
	}

	public List<String> getExceptUrls() {
		return exceptUrls;
	}

	public void setExceptUrls(List<String> exceptUrls) {
		this.exceptUrls = exceptUrls;
	}

	public List<String> getPermissionExceptUrls() {
		return permissionExceptUrls;
	}

	public void setPermissionExceptUrls(List<String> permissionExceptUrls) {
		this.permissionExceptUrls = permissionExceptUrls;
	}


}
