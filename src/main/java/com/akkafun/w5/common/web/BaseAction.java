package com.akkafun.w5.common.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

public abstract class BaseAction {
	
	public static final String COMMON_ERROR_PAGE = "/error/tips";
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 设置错误消息
	 * @param model
	 * @param string
	 */
	protected void addErrorMsg(ModelMap model, String errorMsg) {
		model.addAttribute("errorMsg", errorMsg);
	}
	

}
