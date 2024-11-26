package com.java.springboot.model.mbean;

import javax.management.MXBean;

@MXBean
public interface SystemConfigMXBean {
    int getThreadCount();
    void setThreadCount(int threadCount);
    String getSystemName();
}
