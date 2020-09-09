package com.simple.general.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Demo {

    @GetMapping("/filter")
    public void filter() {
        System.out.println("testDoFilter()");
    }

    @GetMapping("/interceptor")
    public void interceptor() {
        System.out.println("testInterceptor()");
    }

}
