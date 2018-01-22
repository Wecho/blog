package com.wecho.core.servlet.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "Filter",urlPatterns = {"/*"},dispatcherTypes = {DispatcherType.REQUEST,DispatcherType.ERROR})
public class TitleFilter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        MyResponseWrapper myResponseWrapper = new MyResponseWrapper((HttpServletResponse) resp);
        myResponseWrapper.setCharacterEncoding("utf-8");
        chain.doFilter(req, myResponseWrapper);
        String respStr = myResponseWrapper.getResult().replace("{title}","nihao");
        resp.getOutputStream().write(
                respStr.getBytes("utf-8"));
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
