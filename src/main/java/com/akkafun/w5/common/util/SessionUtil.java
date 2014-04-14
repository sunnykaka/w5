package com.akkafun.w5.common.util;

import com.akkafun.w5.user.model.User;

/**
 * Created by liubin on 14-4-13.
 */
public class SessionUtil {

    public static boolean isAdmin(User user) {
        if(user == null) return false;
        if(user.getRole() == null) return false;
        if(user.getRole().getAdmin() == null) return false;
        return user.getRole().getAdmin();

    }

}
