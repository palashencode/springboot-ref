package com.java.springboot.model.mbean;

public class SystemConfig implements SystemConfigMXBean {
    private int threadCount = 10;
    private String systemName = "MySystem";

    @Override
    public int getThreadCount() {
        return threadCount;
    }

    @Override
    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public String getSystemName() {
        return systemName;
    }
}
