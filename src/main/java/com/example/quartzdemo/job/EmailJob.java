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
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            String to = context.getMergedJobDataMap().getString("to");
            emailService.sendEmail(to);
        } catch (Exception e) {
            System.err.println("任務執行失敗：" + e.getMessage());
            JobExecutionException ex = new JobExecutionException(e);
            ex.setRefireImmediately(false); // 是否立即重試
            throw ex;
        }
    }

}
