package com.mobileSE.chatdiary;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class ChatDiaryApplication {
    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }
    public static void main(String[] args) {
        SpringApplication.run(ChatDiaryApplication.class, args);
    }

}
