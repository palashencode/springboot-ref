package com.java.springboot.controllers;

import com.java.springboot.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/v1/envinfo")
@Slf4j
public class EnvironmentInfoController {

    @Value("${secret.key:dummy}")
    private String secretKey;

    @Autowired
    private Environment env;

    @GetMapping("/rmxbean")
    public Map<String, Object> getRMXBeanInfo(){
        Map<String, Object> details = new LinkedHashMap<>();
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        details.put("RuntimeMXBean.getInputArguments()",runtimeMxBean.getInputArguments().toString());
        details.put("RuntimeMXBean.getClassPath()",runtimeMxBean.getClassPath());
        log.info(runtimeMxBean.getClassPath());
        details.put("RuntimeMXBean.getLibraryPath()",runtimeMxBean.getLibraryPath());
        details.put("RuntimeMXBean.getManagementSpecVersion()",runtimeMxBean.getManagementSpecVersion());
        details.put("RuntimeMXBean.getName()",runtimeMxBean.getName());
        details.put("RuntimeMXBean.getPid()",runtimeMxBean.getPid());
        details.put("RuntimeMXBean.getStartTime()",runtimeMxBean.getStartTime());
        details.put("RuntimeMXBean.getUptime()",runtimeMxBean.getUptime());
        details.put("RuntimeMXBean.getVmName()",runtimeMxBean.getVmName());
        details.put("RuntimeMXBean.getVmVendor()",runtimeMxBean.getVmVendor());
        details.put("RuntimeMXBean.getSystemProperties()",runtimeMxBean.getSystemProperties());
        return details;
    }

    @GetMapping()
    public Map<String, Object> getEnvInfo(){
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("secret.key", secretKey);
        add("JAVA_HOME", details);
        add("CLASSPATH", details);
        return details;
    }

    private void add(String envVar, Map<String, Object> map){
        map.put(envVar, env.getProperty(envVar));
    }
}
