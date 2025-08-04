package com.example.quartzdemo.config;

import com.example.quartzdemo.job.SimpleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.quartzdemo.job.EmailJob;


@Configuration
public class QuartzConfig {


    @Bean
    public JobDetail SimpleJobDetail() {
        return JobBuilder.newJob(SimpleJob.class)
                .withIdentity("SimpleJob")
                .storeDurably() // 記得持久化（避免沒有觸發器時被刪除）
                .build();
    }

    @Bean
    public Trigger SimpleJobTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");

        return TriggerBuilder.newTrigger()
                .forJob(SimpleJobDetail())
                .withIdentity("SimpleTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }




    @Bean
    public JobDetail emailJobDetail() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("to", "user@example.com"); // 傳參數

        return JobBuilder.newJob(EmailJob.class)
                .withIdentity("emailJob")
                .setJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger emailJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(emailJobDetail())
                .withIdentity("emailTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("15 * * * * ?")) // 每分鐘第 15 秒
                .build();
    }
}