package com.akkafun.w5.order.web;

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
import com.akkafun.w5.common.web.WebHolder;
import com.akkafun.w5.order.model.OrderBase;
import com.akkafun.w5.order.service.OrderService;
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
public class OrderAction extends BaseAction {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@Autowired
	protected SessionProvider sessionProvider;

	W5Application app = (W5Application)Platform.getInstance().getApp();
	
    @RequestMapping(value="/my/order/add")
    public String addMyOrder(ModelMap model) {

        return "/my/order/add";
    }

    @RequestMapping(value="/my/order/update")
    public String updateMyOrder(ModelMap model) {

        return "/my/order/update";
    }

    @RequestMapping("/my/order/save")
    public String saveMyOrder(HttpServletRequest request, @ModelAttribute("order") OrderBase order,
                              BindingResult results, ModelMap model) {
        if(!results.hasErrors()) {
            if(!userService.isUsernameExist(order.getCustomerUsername(), null)) {
                results.reject("", "会员名称不存在");
            }
        }

        if(results.hasErrors()) {
            //数据验证失败
            if(EntityUtil.isCreate(order)) {
                return addMyOrder(model);
            } else {
                return updateMyOrder(model);
            }
        }

        orderService.saveOrder(order, WebHolder.getUser());

        return "redirect:/my/order/list.action";
    }


    @RequestMapping(value="/my/order/list")
    public String listMyOrder(HttpServletRequest request, ModelMap model) {

        Map<String, String[]> params = Servlets.getParametersStartingWith(request, "sParam_");
        User user = WebHolder.getUser();
        if(user.getType().equals(UserType.CUSTOMER)) {
            params.put("customerId", new String[]{String.valueOf(user.getId())});
        } else {
            params.put("coachId", new String[]{String.valueOf(user.getId())});
        }
        model.put("orders", orderService.findByKey(params, PageEngineFactory.getPageEngine(request)));

        return "/my/order/list";
    }

    @RequestMapping(value="/my/order/confirm")
    public String confirmMyOrder(@ModelAttribute("order") OrderBase order, RedirectAttributes redirectAttributes) {

        try {
            if(!WebHolder.getUser().getId().equals(order.getCustomerId())) {
                redirectAttributes.addAttribute("errorMsg", "只能确认自己的订单");
            } else {
                orderService.confirmOrder(order);
            }
        } catch (BusinessException e) {
            redirectAttributes.addAttribute("errorMsg", e.getMessage());
        }

        return "redirect:/my/order/list.action";
    }


    @RequestMapping(value="/order/add")
    public String addOrder(ModelMap model) {

        return "/order/add";
    }

    @RequestMapping(value="/order/update")
    public String updateOrder(ModelMap model) {

        return "/order/update";
    }

    @RequestMapping("/order/save")
    public String saveOrder(HttpServletRequest request, @ModelAttribute("order") OrderBase order,
                              BindingResult results, ModelMap model) {
        if(!results.hasErrors()) {
            if(!userService.isUsernameExist(order.getCustomerUsername(), null)) {
                results.reject("", "会员名称不存在");
            }
        }
        if(!results.hasErrors()) {
            if(!userService.isUsernameExist(order.getCoachUsername(), null)) {
                results.reject("", "陪练名称不存在");
            }
        }

        if(results.hasErrors()) {
            //数据验证失败
            if(EntityUtil.isCreate(order)) {
                return addOrder(model);
            } else {
                return updateOrder(model);
            }
        }

        orderService.saveOrder(order, userService.getByUsername(order.getCoachUsername()));

        return "redirect:/order/list.action";
    }

    @RequestMapping(value="/order/list")
    public String listOrder(HttpServletRequest request, ModelMap model) {

        Map<String, String[]> params = Servlets.getParametersStartingWith(request, "sParam_");
        model.put("orders", orderService.findByKey(params, PageEngineFactory.getPageEngine(request)));

        return "/order/list";
    }

    @RequestMapping(value="/order/invalid")
    public String invalidOrder(@ModelAttribute("order") OrderBase order, RedirectAttributes redirectAttributes) {
        try {
            orderService.invalidOrder(order);
        } catch (BusinessException e) {
            redirectAttributes.addAttribute("errorMsg", e.getMessage());
        }

        return "redirect:/order/list.action";
    }

    @RequestMapping(value="/order/confirm")
    public String confirmOrder(@ModelAttribute("order") OrderBase order, RedirectAttributes redirectAttributes) {

        try {
            orderService.confirmOrder(order);
        } catch (BusinessException e) {
            redirectAttributes.addAttribute("errorMsg", e.getMessage());
        }

        return "redirect:/order/list.action";
    }


    @ModelAttribute("order")
    public OrderBase getOrder(Long orderId, ModelMap model) {
        if(!ValidateUtil.isNullOrZero(orderId) && !model.containsKey("order")) {
            return orderService.get(orderId);
        } else {
            OrderBase order = new OrderBase();
            return order;
        }
    }
}
