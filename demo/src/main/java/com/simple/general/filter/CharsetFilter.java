package com.simple.general.filter;


import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器
 *
 * @author wcy
 * @date 2020/9/4 14:37
 */
@WebFilter(filterName = "CharsetFilter",
        urlPatterns = "/*",
        initParams = {@WebInitParam(name = "charset", value = "utf-8")})
public class CharsetFilter implements Filter {

    private String filterName;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filterName = filterConfig.getFilterName();
        String charset = filterConfig.getInitParameter("charset");
        System.out.println("过滤器名称:" + filterName);
        System.out.println("字符集编码:" + charset);

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println(filterName + ":doFilter()");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println(filterName + "销毁");
    }
}
