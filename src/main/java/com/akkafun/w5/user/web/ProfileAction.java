package com.akkafun.w5.user.web;

import com.akkafun.platform.Platform;
import com.akkafun.platform.common.web.page.PageEngineFactory;
import com.akkafun.platform.common.web.session.SessionProvider;
import com.akkafun.platform.exception.AppException;
import com.akkafun.platform.util.Encrypter;
import com.akkafun.platform.util.EntityUtil;
import com.akkafun.platform.util.Servlets;
import com.akkafun.platform.util.ValidateUtil;
import com.akkafun.w5.W5Application;
import com.akkafun.w5.code.service.CodeService;
import com.akkafun.w5.common.web.BaseAction;
import com.akkafun.w5.common.web.WebHolder;
import com.akkafun.w5.user.model.User;
import com.akkafun.w5.user.model.UserType;
import com.akkafun.w5.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

@Controller
public class ProfileAction extends BaseAction {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;


    @RequestMapping(value="/my/profile", method = RequestMethod.GET)
    public String profilePage(ModelMap model) {

        return "/my/profile";
    }


    @RequestMapping(value="/my/profile", method = RequestMethod.POST)
    public String saveCustomer(HttpServletRequest request, @ModelAttribute("user") @Valid User user,
                               BindingResult results, ModelMap model) {


        userService.save(user);
        User loginUser = WebHolder.getUser();
        loginUser.setName(user.getName());
        loginUser.setAlipay(user.getAlipay());
        loginUser.setBankcard(user.getBankcard());
        loginUser.setEmail(user.getEmail());
        loginUser.setGender(user.getGender());
        loginUser.setIdcard(user.getIdcard());
        loginUser.setMobile(user.getMobile());
        loginUser.setNickname(user.getNickname());
        loginUser.setQq(user.getQq());
        loginUser.setYy(user.getYy());
        loginUser.setRemark(user.getRemark());

        return "redirect:/my/profile.action";
    }


    @ModelAttribute("user")
    public User getUser(Long userId, ModelMap model) {
        if(!ValidateUtil.isNullOrZero(userId) && !model.containsKey("user")) {
            return userService.get(userId);
        }
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("username", "password", "type", "discount", "proportion", "balance", "price", "roleId");
    }
}
