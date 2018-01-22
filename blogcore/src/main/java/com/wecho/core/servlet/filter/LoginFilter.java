package com.wecho.core.servlet.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 判断用户有没有登录
 */
@WebFilter(filterName = "LoginFilter")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpSession httpSession = ((HttpServletRequest)req).getSession(true);
        String user = (String) httpSession.getAttribute("user");
        if(user!=null){
            chain.doFilter(req, resp);
        }else{
            ((HttpServletRequest)req).getRequestDispatcher("/login").forward(req,resp);
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
