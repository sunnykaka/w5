package com.akkafun.w5.dao.test;

import com.akkafun.platform.test.PlatformTest;
import com.akkafun.w5.order.dao.OrderDao;
import com.akkafun.w5.order.model.OrderBase;
import com.akkafun.w5.payslip.dao.PayslipDao;
import com.akkafun.w5.payslip.model.Payslip;
import com.akkafun.w5.payslip.model.PayslipStatus;
import com.akkafun.w5.permission.jaxb.Permissions;
import com.akkafun.w5.permission.service.PermissionService;
import com.akkafun.w5.user.dao.UserDao;
import com.akkafun.w5.user.model.User;
import com.akkafun.w5.user.model.UserType;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.Random;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class SomeDaoTest extends PlatformTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PayslipDao payslipDao;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    @Rollback(false)
    public void test() {
        User user = new User();

        user.setName(RandomStringUtils.randomAlphabetic(6) + "name");
        user.setPrice(new Random().nextDouble());
        user.setType(UserType.COACH);

        userDao.save(user);
        assertThat(user.getId(), notNullValue());
        userDao.getHibernateTemplate().flush();
        userDao.getHibernateTemplate().clear();

        User reloadUser = userDao.get(user.getId());
        assertEquals(reloadUser, user);


        Payslip payslip = new Payslip();
        payslip.setActualSalary(new Random().nextDouble());
        payslip.setUserId(user.getId());
        payslip.setStatus(PayslipStatus.WAIT_PAY);
        payslipDao.save(payslip);

        userDao.getHibernateTemplate().flush();
        userDao.getHibernateTemplate().clear();

        Payslip reloadPayslip = payslipDao.get(payslip.getId());
        assertEquals(reloadPayslip.getUser(), user);


        OrderBase order = new OrderBase();
        order.setCoachUsername(RandomStringUtils.randomAlphabetic(6) + "coachUsername");
        orderDao.save(order);

    }

    private void assertEquals(User reloadUser, User user) {
        assertThat(reloadUser.getName(), is(user.getName()));
        assertThat(reloadUser.getPrice(), is(user.getPrice()));
        assertThat(reloadUser.getType(), is(user.getType()));
        assertThat(reloadUser.getId(), is(user.getId()));
    }

}
