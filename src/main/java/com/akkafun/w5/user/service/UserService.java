package com.akkafun.w5.user.service;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.akkafun.platform.exception.BusinessException;
import com.akkafun.platform.util.EntityUtil;
import com.akkafun.w5.common.Constants;
import com.akkafun.w5.common.util.SessionUtil;
import com.akkafun.w5.common.web.WebHolder;
import com.akkafun.w5.user.model.UserStatus;
import com.akkafun.w5.user.model.UserType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akkafun.w5.W5Application;
import com.akkafun.w5.permission.model.Permission;
import com.akkafun.w5.permission.service.PermissionService;
import com.akkafun.w5.permission.service.RoleService;
import com.akkafun.w5.user.dao.UserDao;
import com.akkafun.w5.user.model.User;
import com.akkafun.platform.Platform;
import com.akkafun.platform.common.dao.Page;
import com.akkafun.platform.exception.AppException;
import com.akkafun.platform.genericdao.search.Filter;
import com.akkafun.platform.genericdao.search.Search;
import com.akkafun.platform.util.Encrypter;
import com.akkafun.platform.util.MD5Encoder;

@Service
public class UserService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    private W5Application app = (W5Application) Platform.getInstance().getApp();

    /**
     * 根据用户名查找用户,只适用于权限文件里的admin这个用户名
     *
     * @param username
     * @return
     */
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userDao.searchUnique(new Search().addFilter(Filter.equal("username", username)));
    }

    /**
     * 根据用户名查找用户,只适用于权限文件里的admin这个用户名
     *
     * @param username
     * @return
     */
    @Transactional(readOnly = true)
    public List<User> findByUsername(String username) {
        return userDao.search(new Search().addFilter(Filter.equal("username", username)));
    }

    @Transactional
    public void save(User user) {
        userDao.save(user);
    }

    @Transactional(readOnly = true)
    public User get(Long userId) {
        return userDao.get(userId);
    }

    /**
     * 修改密码
     *
     * @param user
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @Transactional
    public boolean changePassword(User user, String oldPassword, String newPassword) {
        if (user == null) return false;
        if (!user.getPassword().equals(MD5Encoder.encode(oldPassword))) return false;
        user.setPassword(MD5Encoder.encode(newPassword));
        userDao.save(user);
        return true;
    }

    /**
     * 用cookie登录
     *
     * @param request
     * @return
     */
    @Transactional
    public User loginByCookie(HttpServletRequest request) {
        User user = null;
        int expires = 1209600;    //14天
        //尝试通过cookie登录
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            String userCookie = "";
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                if ("w4userlogin".equals(c.getName())) {
                    userCookie = c.getValue();
                }
            }
            if (userCookie != null && !"".equals(userCookie)) {
                try {
                    String rootripuserlogin = Encrypter.decipher(URLDecoder.decode(userCookie, "UTF-8"));
                    String[] login = rootripuserlogin.split(",");
                    if (Math.abs((new Date().getTime() - Long.parseLong(login[0]))) < (expires * 1000L)) {
                        String passport = login[1];
                        String password = login[2];
                        if (!StringUtils.isBlank(passport) && !StringUtils.isBlank(password)) {
                            try {
                                user = login(passport, password);
                            } catch (AppException e) {
                                log.warn(e.getMessage(), e);
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("cookie解密失败!", e);
                }
            }
        }
        return user;
    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @throws AppException
     */
    @Transactional
    public User login(String username, String password) throws AppException {
        User user = null;
        List<User> users = findByUsername(username);
        if (users.size() > 1) {
            log.error("根据username[{}]查询出来的users数量为{}", username, users.size());
            throw new AppException("帐号登录失败,请咨询管理员");
        }
        if (!users.isEmpty()) {
            user = users.get(0);
        }
        if (user == null) {
            throw new AppException("该帐号不存在");
        }
        if(UserStatus.WAIT_CONFIRM.equals(user.getStatus())) {
            throw new AppException("请等待管理员审核您的帐号");
        }
        if(!UserStatus.NORMAL.equals(user.getStatus())) {
            throw new AppException("您的账号已被禁止登录");
        }
        if (!user.getPassword().equals(MD5Encoder.encode(password))) {
            throw new AppException("密码错误,请重新输入");
        }
        userDao.save(user);
        user.initialize();
        Set<Permission> permissions = user.getRole().getPermissions();
        user.buildPermissionsCache(permissions);

        return user;
    }

    @Transactional(readOnly = true)
    public List<User> findByRole(Long roleId) {
        return userDao.findByRole(roleId);
    }

    @Transactional(readOnly = true)
    public List<User> findCustomerByKey(Map<String, String[]> paramMap, Page page) {
        Search search = new Search().addSortDesc("operator.addTime").setPagination(page);
        search.addFilterEqual("type", UserType.CUSTOMER);

        if (paramMap.containsKey("username") && paramMap.get("username").length > 0) {
            String codeOrName = paramMap.get("username")[0];
            if (!StringUtils.isBlank(codeOrName)) {
                search.addFilter(Filter.like("username", "%" + codeOrName + "%"));
            }
        }
        return userDao.search(search);
    }

    @Transactional(readOnly = true)
    public List<User> findCoachByKey(Map<String, String[]> paramMap, Page page) {
        Search search = new Search().addSortDesc("operator.addTime").setPagination(page);
        search.addFilterEqual("type", UserType.COACH);

        if (paramMap.containsKey("username") && paramMap.get("username").length > 0) {
            String codeOrName = paramMap.get("username")[0];
            if (!StringUtils.isBlank(codeOrName)) {
                search.addFilter(Filter.like("username", "%" + codeOrName + "%"));
            }
        }
        return userDao.search(search);
    }

    /**
     * 判断用户名是否存在
     *
     * @param username
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public boolean isUsernameExist(String username, Long userId) {
        int count = userDao.count(new Search()
                .addFilter(Filter.equal("username", username))
                .addFilter(Filter.notEqual("id", userId)));
        return count > 0;
    }

    @Transactional
    public void saveUser(User user, String newPassword) {
        if(!StringUtils.isBlank(newPassword)) {
            user.setPassword(MD5Encoder.encode(newPassword));
        }
        WebHolder.fillOperatorValues(user);

        if(EntityUtil.isCreate(user) && !SessionUtil.isAdmin(user)) {
            user.setStatus(UserStatus.NORMAL);
            switch (user.getType()) {
                case CUSTOMER: {
                    user.setRoleId(roleService.getCustomerRole().getId());
                    break;
                } case COACH: {
                    user.setRoleId(roleService.getCoachRole().getId());
                    break;
                }
            }
        }

        userDao.save(user);
    }

    @Transactional
    public void register(User user) {

        user.setPassword(MD5Encoder.encode(user.getPassword()));

        WebHolder.fillOperatorValues(user);

        switch (user.getType()) {
            case CUSTOMER: {
                user.setRoleId(roleService.getCustomerRole().getId());
                break;
            } case COACH: {
                user.setRoleId(roleService.getCoachRole().getId());
                break;
            }
        }
        user.setStatus(UserStatus.WAIT_CONFIRM);

        userDao.save(user);
    }

    @Transactional
    public void confirmUser(User user) {
        user.setStatus(UserStatus.NORMAL);
        userDao.save(user);
    }
}
