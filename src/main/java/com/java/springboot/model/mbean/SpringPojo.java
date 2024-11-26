package com.java.springboot.model.mbean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

// https://docs.spring.io/spring-framework/reference/integration/jmx/interface.html
@Component
@Getter
@Setter
@ManagedResource
public class SpringPojo {

    List<String> dataList = new ArrayList<>();

    @ManagedAttribute(description="return datalist", currencyTimeLimit=15)
    public List<String> getDataList() {
        return dataList;
    }

    @ManagedOperation(description="clear")
    public void clear(){
        dataList.clear();
    }

    @ManagedOperation(description="populate")
    public void populate(){
        dataList.add("Sample1"+ Instant.now().toString());
        dataList.add("Sample2"+ Instant.now().toString());
        dataList.add("Sample3"+ Instant.now().toString());
    }

    private int age;
    private String name;

    @ManagedAttribute(description="The Age Attribute", currencyTimeLimit=15)
    public int getAge() {
        return this.age;
    }

    @ManagedAttribute(description="The Name Attribute",
            currencyTimeLimit=20,
            defaultValue="bar",
            persistPolicy="OnUpdate")
    public void setName(String name) {
        this.name = name;
    }

    @ManagedAttribute(defaultValue="foo", persistPeriod=300)
    public String getName() {
        return this.name;
    }

}



