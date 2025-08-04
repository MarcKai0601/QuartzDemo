package com.example.quartzdemo.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class SimpleJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("🕒 Hello Quartz! 現在時間：" + LocalDateTime.now());
    }
}