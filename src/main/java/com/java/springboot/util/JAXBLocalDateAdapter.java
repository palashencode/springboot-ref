package com.java.springboot.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JAXBLocalDateAdapter extends XmlAdapter<String, LocalDate> {
    
    private static final ThreadLocal<DateTimeFormatter> dateFormatter = new ThreadLocal<>(){
        @Override
        protected DateTimeFormatter initialValue(){
            return DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }
    };

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v, dateFormatter.get());
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.format(dateFormatter.get());
    }
}
