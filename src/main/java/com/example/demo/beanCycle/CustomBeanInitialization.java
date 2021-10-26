package com.example.demo.beanCycle;

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

public class CustomBeanInitialization implements InitializingBean {

    private final String SIMPLE_CLASS_NAME =  this.getClass().getSimpleName();

    // 1
    public CustomBeanInitialization() {
        System.out.println("Non-args constructor is called: " + SIMPLE_CLASS_NAME);
    }

    // 2
    @PostConstruct
    public void postConstruct() {
        System.out.println("post construct is called: " + SIMPLE_CLASS_NAME);
    }

    // 3
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("after properties set is finished: " + SIMPLE_CLASS_NAME);
    }

    // 4
    public void initMethod() {
        System.out.println("initMethod is called: " + SIMPLE_CLASS_NAME);
    }

}
