package com.app.inven.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;


@Configuration
public class ApplicationLoggerConfig {
    @Bean
    public Logger logger() {  // Now returns java.util.logging.Logger
        return Logger.getLogger("InventoryLogger");
    }
}