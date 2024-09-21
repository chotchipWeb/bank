package com.chotchip.Notification.service.command;

public interface BotCommandProcessor {
    String processMessage(String message, Long chadId);
}
