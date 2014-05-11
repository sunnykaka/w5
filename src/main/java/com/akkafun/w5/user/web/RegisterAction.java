package com.akkafun.w5.user.web;

import com.akkafun.platform.Platform;
import com.akkafun.platform.common.web.page.PageEngineFactory;
import com.akkafun.platform.common.web.session.SessionProvider;
import com.akkafun.platform.exception.AppException;
import com.akkafun.platform.exception.BusinessException;
import com.akkafun.platform.util.Encrypter;
import com.akkafun.platform.util.EntityUtil;
import com.akkafun.platform.util.Servlets;
import com.akkafun.platform.util.ValidateUtil;
import com.akkafun.w5.W5Application;
import com.akkafun.w5.code.service.CodeService;
import com.akkafun.w5.common.web.BaseAction;
import com.akkafun.w5.user.model.User;
import com.akkafun.w5.user.model.UserType;
import com.akkafun.w5.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RegisterAction extends BaseAction {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	@Autowired
	protected SessionProvider sessionProvider;

	@Autowired
	private CodeService codeService;
	
	W5Application app = (W5Application)Platform.getInstance().getApp();
	


	@RequestMapping(value="/register")
	public String register(HttpServletRequest request, ModelMap model, User user, BindingResult results) {

        if(StringUtils.isBlank(user.getUsername())) {
            results.reject("", "用户名不能为空");
        }
        if(StringUtils.isBlank(user.getPassword())) {
            results.reject("", "用户名不能为空");
        }
        if(user.getType() == null) {
            results.reject("", "用户类型不能为空");
        }
        if(userService.isUsernameExist(user.getUsername(), null)) {
            results.reject("", "用户名已经存在");
        }

        if(results.hasErrors()) {
            return "/login";
        }

        try {
            userService.register(user);
        } catch (BusinessException e) {
            //数据验证失败
            results.reject("", e.getMessage());
            return "/login";
        }

        return "redirect:/login.action";
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("username", "password", "type", "price", "yy", "qq", "mobile");
    }

}
