package com.java.springboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.Conditions;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultHttpLogWriter;
import org.zalando.logbook.core.DefaultSink;

import java.time.Duration;

@Configuration
@Slf4j
public class AppConfig {

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(5000))
                .setReadTimeout(Duration.ofMillis(5000))
                .build();
    }

    /**
     * enabled by `logging.level.org.zalando.logbook.Logbook=TRACE` in application.properties
     * @return
     */
    @Bean
    public Logbook logbook() {
        Logbook logbook = Logbook.builder()
                .condition(Conditions.exclude(Conditions.requestTo("/api/welcome"),
                        Conditions.contentType("application/octet-stream"),
                        Conditions.header("X-Secret", "true")))
                .sink(new DefaultSink(new DefaultHttpLogFormatter(), new DefaultHttpLogWriter()))
                .build();
        return logbook;
    }

}
