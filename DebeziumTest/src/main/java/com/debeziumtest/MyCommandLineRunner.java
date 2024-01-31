package com.debeziumtest;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/4 23:23
 */
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        System.out.println("应用正在运行...");
    }
}
