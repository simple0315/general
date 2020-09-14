package com.simple.general.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * 前后端分离RESTful接口过滤器
 *
 * @author xuguoqin
 */
public class RestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = null;
        if (request instanceof HttpServletRequest) {
            req = (HttpServletRequest) request;
        }
        HttpServletResponse res = null;
        if (response instanceof HttpServletResponse) {
            res = (HttpServletResponse) response;
        }
        if (req != null && res != null) {
            //设置允许传递的参数
            res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
            //设置允许的请求来源
            String origin = Optional.ofNullable(req.getHeader("Origin")).orElse(req.getHeader("Referer"));
            res.setHeader("Access-Control-Allow-Origin", origin);
            //设置允许带上cookie
            res.setHeader("Access-Control-Allow-Credentials", "true");
            //设置允许的请求方法
            res.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
