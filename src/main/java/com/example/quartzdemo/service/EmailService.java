package com.example.quartzdemo.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendEmail(String to) {
//        ERROR Test
//        if (true) throw new RuntimeException("模擬錯誤");

        System.out.println("📧 模擬發送 Email 給：" + to);
    }
}
