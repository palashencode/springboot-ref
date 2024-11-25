package com.java.springboot.model.mbean;

import lombok.extern.slf4j.Slf4j;

// Standard Bean Config
@Slf4j
public class Sample implements SampleMBean {
    public void sayHello() {
        System.out.println("Syso : Sample Statement!");
        log.info("LOG : Sample Statement!");
    }
    public int addNumbers(int x, int y) {
        return x + y;
    }
}