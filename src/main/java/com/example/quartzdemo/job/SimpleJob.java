package com.example.quartzdemo.job;

import org.quartz.*;

import java.time.LocalDateTime;

public class SimpleJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("🕒 Hello Quartz! 現在時間：" + LocalDateTime.now());
    }
}