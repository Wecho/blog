package com.wecho.core.util.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class WebUtil {
    public static boolean checkUserLoginStatus(HttpServletRequest servletRequest){
        HttpSession httpSession = servletRequest.getSession();
        return httpSession.getAttribute("user")!=null;
    }
}