package com.coolcloud.sacw.common.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class SystemUtil {

    private static final String ADMIN_USERNAME = "admin";

    /**
     * 当前登录用户名
     * 
     * @return
     */
    public static String currentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * 当前登录用户名
     * 
     * @return
     */
    public static boolean isAdmin() {
        String usernmae = currentUser();
        return ADMIN_USERNAME.equals(usernmae);
    }
    
   

}
