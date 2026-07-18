package com.codeying.component.config;

import com.codeying.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

@Configuration
@EnableScheduling
public class ScheduleConfiguration extends BaseController implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private TaskScheduler taskScheduler;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //5秒一次
        taskScheduler.schedule(()->{
            //System.out.println("hello");
        }, new CronTrigger("0/5 * * * * *"));
    }

}