package com.example.demo;

import com.example.demo.beanCycle.CustomBeanInitialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.TreeMap;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean(initMethod = "initMethod")
    @RequestMapping
    public CustomBeanInitialization getDefault() {
        return new CustomBeanInitialization();
    }
}
