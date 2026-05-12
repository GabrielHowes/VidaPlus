package com.example.vidaplus.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


@Configuration
public class AsyncConfig {

    @Bean("virtualThreadExecutor")
    public Executor virtualThreadExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

}
