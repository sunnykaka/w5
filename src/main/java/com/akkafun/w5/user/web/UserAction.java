package com.akkafun.w5.user.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.akkafun.w5.W5Application;
import com.akkafun.w5.code.service.CodeService;
import com.akkafun.w5.common.web.BaseAction;
import com.akkafun.w5.user.model.User;
import com.akkafun.w5.user.service.UserService;
import com.akkafun.platform.Platform;
import com.akkafun.platform.common.web.page.PageEngineFactory;
import com.akkafun.platform.common.web.session.SessionProvider;
import com.akkafun.platform.exception.AppException;
import com.akkafun.platform.util.Encrypter;
import com.akkafun.platform.util.EntityUtil;
import com.akkafun.platform.util.Servlets;
import com.akkafun.platform.util.ValidateUtil;

@Controller
public class UserAction extends BaseAction {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	@Autowired
	protected SessionProvider sessionProvider;

	@Autowired
	private CodeService codeService;
	
	W5Application app = (W5Application)Platform.getInstance().getApp();
	
	@RequestMapping(value="/default")
	public String defaultPage(ModelMap model) {
		return "redirect:/index.action";
	}

	@RequestMapping(value="/index")
	public String index(HttpServletRequest request, ModelMap model) {

		return "/index";
	}


	@RequestMapping(value="/login_submit")
	public String login(HttpServletRequest request, HttpServletResponse response, String username, String password, String saveCookie,
			@ModelAttribute("user") User aModel, BindingResult results, ModelMap model, RedirectAttributes redirectAttributes) {

		int expires = 1209600;	//14天
		User user = null;
		if(!StringUtils.isBlank(username) && !StringUtils.isBlank(password)) {
			//通过用户名密码登录
			if(!ValidateUtil.isLengthBetween(password, 6, 20)) {
				results.reject("", "密码的长度应在6-20个字符之间");
			}
			if(!results.hasErrors()) {
				try {
					user = userService.login(username, password);
				} catch (AppException e) {
					//数据验证失败
					results.reject("", e.getMessage());
				}
			}
		}
		if(user == null) {
			//登录失败
			return index(request, model);
		}
		//将user设置到session
		sessionProvider.setAttr(request, response, User.SESSION_KEY, user);
		if("checked".equals(saveCookie)) {
			//保存cookie
			try {
				Cookie ce = new Cookie("w4userlogin", URLEncoder.encode(Encrypter.cipher(new Date().getTime() + "," + username + "," + password), "UTF-8"));
				ce.setPath("/");
				ce.setMaxAge(expires);
				response.addCookie(ce);
			} catch (UnsupportedEncodingException e) {
				log.error("", e);
			}
		}

		return "redirect:/admin/admin.action";

	}

	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		if(sessionProvider.getAttr(request, User.SESSION_KEY) != null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					Cookie c = cookies[i];
					if ("w4userlogin".equals(c.getName())) {
						c.setPath("/");
						c.setMaxAge(0);
						response.addCookie(c);
					}
				}
			}
			sessionProvider.logout(request, response);
		}
		return "redirect:/index.action";
	}

	@RequestMapping(value="/np/user/change_password", method=RequestMethod.GET)
	public String changePasswordPage(HttpServletRequest request) {
		return "/user/change_password";
	}

	@RequestMapping(value="/np/user/change_password", method=RequestMethod.POST)
	public String changePassword(HttpServletRequest request, String oldPassword, String newPassword, ModelMap model) {
		boolean success = false;
		if(!ValidateUtil.isLengthBetween(newPassword, 6, 20)) {
			//新密码格式不符合规范
			addErrorMsg(model, "密码需要在6~20位之间");
		} else {
			User user = (User)sessionProvider.getAttr(request, User.SESSION_KEY);
			success = userService.changePassword(user, oldPassword, newPassword);
			if(!success) {
				addErrorMsg(model, "密码输入错误");
			}
		}
		model.addAttribute("success", success);
		return "/user/change_password";
	}

	@RequestMapping("/user/list")
	public String listUser(HttpServletRequest request, ModelMap model) {
		Map<String, String[]> params = Servlets.getParametersStartingWith(request, "sParam_");
		model.put("users", userService.findByKey(params, PageEngineFactory.getPageEngine(request)));
		return "/user/list";
	}

	@RequestMapping("/user/add")
	public String addUser(ModelMap model) {
		return "/user/add";
	}

	@RequestMapping("/user/update")
	public String updateUser(ModelMap model) {
		return "/user/update";
	}

	@RequestMapping("/user/save")
	public String saveUser(HttpServletRequest request, @ModelAttribute("user") @Valid User user, BindingResult results,
			String newPassword, ModelMap model) {
		if(!results.hasErrors()) {
            if(userService.isUsernameExist(user.getUsername(), user.getId())) {
                results.reject("", "登录名称已经存在");
            }
		}

		if(results.hasErrors()) {
			//数据验证失败
			if(EntityUtil.isCreate(user)) {
				return addUser(model);
			} else {
				return updateUser(model);
			}
		}

//		userService.saveUser(user, newPassword);

		return "redirect:/user/list.action";
	}

	@ModelAttribute("user")
	public User getUser(Long userId, ModelMap model) {
		if(!ValidateUtil.isNullOrZero(userId) && !model.containsKey("user")) {
			return userService.get(userId);
		} else {
			return new User();
		}
	}

}