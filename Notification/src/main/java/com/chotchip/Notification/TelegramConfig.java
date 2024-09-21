package com.chotchip.Notification;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TelegramConfig {
    @Value("${telegram.name}")
    private String name;

    @Value("${telegram.token}")
    private String token;
}
