package com.java.springboot.config;

import com.java.springboot.service.LabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.management.timer.Timer;
import java.time.Instant;

@Configuration
@Slf4j
@Component
@ConditionalOnProperty(value = "app.scheduler.enabled", matchIfMissing = false, havingValue = "true")
public class SchedulerConfig {

    private LabelService labelService;

    /**
     * As of Spring 4.3, classes with a single constructor can omit the @Autowired annotation.
     * Advantage of Constructor Injection - it 'forces' and hence 'guarantees' dependency
     * injection at object creation.
     * @param labelService
     */
    public SchedulerConfig(LabelService labelService){
        this.labelService = labelService;
    }

    @Scheduled(fixedDelay = Timer.ONE_MINUTE)
    public void reloadLabelCache(){
        labelService.evictAllCache();
        // this can be used to preload the caches
//        labelService.getAll();            //NOSONAR
    }

    @Scheduled(cron = "${app.scheduler.label.eviction.cron}") // every min
    public void schedulerLog(){
        log.info("info : dummy scheduler, logging every 1 min."+ Instant.now());
    }

}
