package com.simple.general.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Demo {

    @GetMapping("/demo")
    public void test() {
        System.out.println("testDoFilter()");
    }

}
