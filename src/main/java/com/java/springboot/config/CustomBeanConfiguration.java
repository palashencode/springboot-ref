package com.java.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Configuration
public class CustomBeanConfiguration {

    @Bean
    HandlerExceptionResolver errorHandler() {
        return (request, response, handler, ex) -> {
            ModelAndView model = new ModelAndView("error");
            String errMsg = "Error : " + ex;
            model.addObject("errmsg", errMsg);
            return model;
        };
    }

}
