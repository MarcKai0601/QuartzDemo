package com.example.quartzdemo.job;

import com.example.quartzdemo.service.EmailService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailJob implements Job {

    @Autowired
    private EmailService emailService;

    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap dataMap = context.getMergedJobDataMap();
        String to = dataMap.getString("to");

        emailService.sendEmail(to);
    }
}
