package ru.practicum.shareit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/application.yaml")
public class AppConfig {
}