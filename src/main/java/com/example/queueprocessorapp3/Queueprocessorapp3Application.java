package com.example.queueprocessorapp3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients

public class Queueprocessorapp3Application {

    public static void main(String[] args) {
        SpringApplication.run(Queueprocessorapp3Application.class, args);
    }

}
