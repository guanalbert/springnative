package com.example.demo.quartz;

import java.util.Date;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
public class MyJob{
    @Scheduled(cron = "0/5 * * * * ?")
    public void everyFiveSeconds() {
        log.info("Periodic task: {}" , new Date());
    }
    
}
