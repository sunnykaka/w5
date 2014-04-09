package com.akkafun.w5.permission.util;

import java.util.Map;

import com.akkafun.w5.user.model.UserType;
import org.apache.commons.lang3.StringUtils;

import com.akkafun.w5.common.web.WebHolder;
import com.akkafun.w5.permission.model.Permission;
import com.akkafun.w5.user.model.User;

public class PermissionUtil {
	
	/**
	 * 检查权限
	 * @param user
	 * @param url
	 * @return
	 */
	public static boolean isPermitted(User user, String url) {
		if(user == null || StringUtils.isBlank(url)) return false;
		if(user.getRole().getAdmin()) return true;
		Map<String, Permission> permissionsCache = user.getPermissionsCache();
		if(permissionsCache != null) {
			if(permissionsCache.get(url) != null) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 当前登录用户是不是顾客
	 * @return
	 */
	public static boolean isCustomer() {
		return isSpecUserType(UserType.CUSTOMER, WebHolder.getUser());
	}
	

	/**
	 * 当前登录用户是不是陪练
	 * @return
	 */
	public static boolean isCoach() {
		return isSpecUserType(UserType.COACH, WebHolder.getUser());
	}


	private static boolean isSpecUserType(UserType userType, User user) {
		if(user == null) return false;
		return userType.value.equals(user.getType());
	}
}
