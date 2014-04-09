package com.akkafun.platform.springmvc;

import java.util.Date;

import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * 数据绑定初始化类
 * @author liubin
 * @since Feb 14, 2011
 * @version 1.0
 */
public class BindingInitializer implements WebBindingInitializer {
	
	private Validator validator;
	
	/**
	 * 初始化数据绑定
	 */
	public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(Date.class, new DateTypeEditor());
		if (this.validator != null && binder.getTarget() != null &&
				this.validator.supports(binder.getTarget().getClass())) {
			binder.setValidator(this.validator);
		}
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}
