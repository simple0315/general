package com.simple.general.demo;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/test")
public class Demo {

    @GetMapping("/demo")
    public JSONObject test() {
        log.info("testDoFilter()");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "tom");
        jsonObject.put("age", "1");
        return jsonObject;
    }

    @GetMapping("/interceptor")
    public void interceptor() {
        log.info("testInterceptor()");
    }

    @GetMapping("/session")
    public JSONObject testSession(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        log.info("sessionId:" + request.changeSessionId());
        log.info("authType:" + request.getAuthType());
        log.info("method:" + request.getMethod());
        log.info("contextPath:" + request.getContextPath());
        log.info("pathInfo:" + request.getPathInfo());
        log.info("remoteUser:" + request.getRemoteUser());
        log.info("remoteAddr:" + request.getRemoteAddr());
        log.info("remoteHost:" + request.getRemoteHost());
        log.info("remotePort:" + request.getRemotePort());
        log.info("requestUri:" + request.getRequestURI());
        log.info("contentType:" + request.getContentType());
        log.info("requestSessionId:" + request.getRequestedSessionId());
        log.info("session:" + request.getSession());
        log.info("---------------------response------------------------");

        Cookie cookie = new Cookie("username", "tom");
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);
        response.setStatus(401);
        response.setHeader("test", "response");

        session.setAttribute("username", "tom");
        session.setAttribute("work", "java");
        session.setMaxInactiveInterval(60 * 60);
        log.info("sessionId:" + session.getId());
        log.info("maxInactiveInterval:" +session.getMaxInactiveInterval());
        log.info("lastAccessTime:" + session.getLastAccessedTime());
        log.info("contentPath:" + session.getServletContext().getContextPath());
        log.info("serverInfo:" + session.getServletContext().getServerInfo());
        log.info("----------------------session-----------------------");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rtn", 0);
        jsonObject.put("msg", "ok");
        return jsonObject;
    }

}
