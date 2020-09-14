package com.simple.general;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = "com.simple.general",exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@ServletComponentScan
public class GeneralWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneralWebApplication.class, args);
    }

}
