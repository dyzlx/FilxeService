package com.dyz.filxeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class FilxeserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FilxeserviceApplication.class, args);
    }
}
