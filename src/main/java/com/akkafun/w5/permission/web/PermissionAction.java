package com.akkafun.w5.permission.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.akkafun.w5.common.web.BaseAction;

@Controller
public class PermissionAction extends BaseAction {
	
	@RequestMapping("/error/no_permission")
	public String noPermission() {
		return "/error/noPermission";
	}
	
}
