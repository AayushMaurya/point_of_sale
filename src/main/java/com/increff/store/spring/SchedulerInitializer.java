package com.increff.store.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class SchedulerInitializer {

    public void getSchedulerConfigClass(){
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(SchedulerConfig.class);
    }
}
