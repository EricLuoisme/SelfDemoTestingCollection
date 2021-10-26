package com.example.demo.beanCycle;

import org.springframework.beans.factory.DisposableBean;

public class CustomBeanDestroy implements DisposableBean {

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy the bean: " + this.getClass().getSimpleName());
    }
}
