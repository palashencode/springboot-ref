package com.java.springboot.controllers;


import com.github.javafaker.Faker;
import com.java.springboot.entities.User;
import com.java.springboot.model.mbean.Sample;
import com.java.springboot.model.mbean.SampleMBean;
import com.java.springboot.model.mbean.SystemConfig;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/jvmanalysis")
public class JVMAnalysisController {

    @Operation(summary = "open 'jconsole' to check the MBean registered")
    @GetMapping("/mxbean/register")
    public String mxbeanRegister(@RequestParam(name = "id", defaultValue = "0") Integer id) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("com.example.custom:type=SystemConfig-"+id);
        SystemConfig configMXBean = new SystemConfig();
        mbs.registerMBean(configMXBean, name);

        String res = "MXBean registered. at "+Instant.now();
        log.info(res);
        return res;
    }

    @Operation(summary = "open 'jconsole' to check the MBean registered")
    @GetMapping("/mbean/register")
    public String mbeanRegister(@RequestParam(name = "id", defaultValue = "0") Integer id) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("com.example.custom:type=Sample-"+id);
        Sample mbean = new Sample();
        mbs.registerMBean(mbean, name);

        String res = "MBean registered. at "+Instant.now();
        log.info(res);
        return res;
    }


    @GetMapping("/ooo/maxarraysize")
    public String maxarraysize(){
        log.info("Creating array of size {}", Integer.MAX_VALUE);
        int[] arr = new int[Integer.MAX_VALUE];
        return "success";
    }

    @GetMapping("/ooo/directbuffer")
    public void directbuffer(){
        while(true){
            // Allocate a large direct byte buffer
            ByteBuffer buffer = ByteBuffer.allocateDirect(Integer.MAX_VALUE);
        }
    }

    @GetMapping("/heapfill")
    public String heapFill(@RequestParam(name = "count") Integer count){
        List<User> users = new ArrayList<>();
        addUsersToList(users, count, true);
        return count+" objects created at "+ Instant.now();
    }

    @GetMapping("/heapfillrelease")
    public String heapfillrelease(@RequestParam(name = "count") Integer count,
                                  @RequestParam(name = "repeat") Integer repeat,
                                @RequestParam(name = "sleep", defaultValue = "0") Integer sleep,
                                  @RequestParam(name = "memoryleak", defaultValue = "false") Boolean memoryLeak){
        List<User> users = new ArrayList<>();
        for (int j = 0; j < repeat; j++) {
            addUsersToList(users, count, true);
            try {
                Thread.sleep(sleep*1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(!memoryLeak){
                users.clear();
            }
        }

        return count+" objects created at "+ Instant.now();
    }

    private List<User> addUsersToList(List<User> users, Integer count, Boolean printLog){
        Faker faker = new Faker();
        for (int i = 0; i < count; i++) {
            users.add(getFakeUser(faker));
            if(printLog)
                log.info("User created {}", i);
        }
        return users;
    }

    private User getFakeUser(Faker faker){
        return User.builder()
                .userName(new String(faker.name().fullName()))
                .city(new String(faker.address().city()))
                .dateOfJoining(LocalDate.now())
                .build();
    }
}

