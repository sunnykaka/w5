package com.akkafun.w5.order.service;

import com.akkafun.platform.Platform;
import com.akkafun.platform.common.dao.Page;
import com.akkafun.platform.exception.BusinessException;
import com.akkafun.platform.genericdao.search.Search;
import com.akkafun.platform.util.EntityUtil;
import com.akkafun.w5.W5Application;
import com.akkafun.w5.common.util.OperatorUtil;
import com.akkafun.w5.common.web.WebHolder;
import com.akkafun.w5.order.dao.OrderDao;
import com.akkafun.w5.order.model.OrderBase;
import com.akkafun.w5.order.model.OrderStatus;
import com.akkafun.w5.user.dao.UserDao;
import com.akkafun.w5.user.model.User;
import com.akkafun.w5.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    private W5Application app = (W5Application) Platform.getInstance().getApp();


    @Transactional(readOnly = true)
    public OrderBase get(Long orderId) {
        return orderDao.get(orderId);
    }


    @Transactional(readOnly = true)
    public List<User> findByKey(Map<String, String[]> paramMap, Page page) {
        Search search = new Search().addSortDesc("operator.addTime").setPagination(page);

        if (paramMap.containsKey("coachId") && paramMap.get("coachId").length > 0) {
            String coachId = paramMap.get("coachId")[0];
            if (!StringUtils.isBlank(coachId)) {
                search.addFilterEqual("coachId", Long.parseLong(coachId));
            }
        }
        return orderDao.search(search);
    }

    @Transactional
    public void saveOrder(OrderBase order, User coach) {
        boolean isCreate = EntityUtil.isCreate(order);
        if(isCreate) {
            order.setCoachId(coach.getId());
            User customer = userService.getByUsername(order.getCustomerUsername());
            order.setCustomerId(customer.getId());
            order.setStatus(OrderStatus.WAIT_CONFIRM);
            order.setDiscount(customer.getDiscount());
        } else {
            if(!OrderStatus.WAIT_CONFIRM.equals(order.getStatus())) {
                throw new BusinessException("只有待处理状态的订单才能进行修改");
            }
        }
        order.setPayment(new BigDecimal(order.getPrice() * order.getCoachHour())
                .multiply(new BigDecimal(order.getDiscount() / 100d))
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() );

        WebHolder.fillOperatorValues(order);
        orderDao.save(order);
    }

    @Transactional
    public void invalidOrder(OrderBase order) {
        if(!order.getStatus().equals(OrderStatus.WAIT_CONFIRM)) {
            throw new BusinessException("待处理状态的订单才能作废");
        }
        order.setStatus(OrderStatus.INVALID);
        orderDao.save(order);
    }

    @Transactional
    public void confirmOrder(OrderBase order) {
        User customer = order.getCustomer();
        User coach = order.getCoach();
        if(customer.getBalance() < order.getPayment()) {
            throw new BusinessException(String.format("会员余额不足以支付该笔陪练费用.会员余额[%.2f元],陪练费用[%.2f元]",
                    customer.getBalance(), order.getPayment()));
        }
        customer.setBalance(new BigDecimal(customer.getBalance() - order.getPayment()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        coach.setBalance(new BigDecimal(coach.getBalance() + order.getCoachSalary()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        order.setStatus(OrderStatus.CONFIRMED);

        userDao.save(customer);
        userDao.save(coach);
        orderDao.save(order);

    }
}
