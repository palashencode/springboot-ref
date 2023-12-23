package com.java.springboot.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class JAXBZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {

    private static final ThreadLocal<DateTimeFormatter> dateFormatter = new ThreadLocal<>(){
        @Override
        protected DateTimeFormatter initialValue(){
            return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        }
    };

    @Override
    public ZonedDateTime unmarshal(String v) throws Exception {
        return ZonedDateTime.parse(v, dateFormatter.get());
    }

    @Override
    public String marshal(ZonedDateTime v) throws Exception {
        return v.format(dateFormatter.get());
    }

}
