package com.simple.general.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Demo {

    @GetMapping("/demo")
    public JSONObject test() {
        System.out.println("testDoFilter()");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "tom");
        jsonObject.put("age", "1");
        return jsonObject;
    }

    @GetMapping("/interceptor")
    public void interceptor() {
        System.out.println("testInterceptor()");
    }

}
