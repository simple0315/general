package com.simple.general.filter;

import com.alibaba.fastjson.JSONObject;
import com.simple.general.response.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * shiro跨域
 *
 * @author Mr.Wu
 * @date 2020/5/26 01:34
 */
@Slf4j
public class LoginAuthenticationFilter extends FormAuthenticationFilter {

    public LoginAuthenticationFilter() {
        super();
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest) request).getMethod().toUpperCase().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setStatus(HttpServletResponse.SC_OK);
        res.setCharacterEncoding("UTF-8");
        PrintWriter writer = res.getWriter();
        Subject subject = getSubject(request, response);
        JSONObject jsonObject = new JSONObject();
        if (subject == null) {
            jsonObject.put("rtn", ResponseEnum.NOT_LOGIN.getCode());
            jsonObject.put("msg", ResponseEnum.NOT_LOGIN.getMessage());
        } else {
            jsonObject.put("rtn", ResponseEnum.UNAUTHORIZED.getCode());
            jsonObject.put("msg", ResponseEnum.UNAUTHORIZED.getMessage());
        }
        writer.write(jsonObject.toJSONString());
        writer.close();
        return false;
    }

}
