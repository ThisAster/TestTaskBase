package com.thisaster.testtask;

import org.springframework.boot.SpringApplication;

public class TestTesttaskApplication {

    public static void main(String[] args) {
        SpringApplication.from(TesttaskApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
