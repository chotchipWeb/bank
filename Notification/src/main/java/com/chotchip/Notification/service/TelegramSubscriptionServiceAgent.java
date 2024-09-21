package com.chotchip.Notification.service;

import com.chotchip.Notification.TelegramConfig;
import com.chotchip.Notification.service.command.BotCommandProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Service
public class TelegramSubscriptionServiceAgent extends TelegramLongPollingBot {
    private final TelegramConfig telegramConfig;
    private final Map<String, BotCommandProcessor> commands;

    @Autowired
    public TelegramSubscriptionServiceAgent(TelegramConfig telegramConfig, Map<String, BotCommandProcessor> commands) {
        super(telegramConfig.getToken());
        this.telegramConfig = telegramConfig;
        this.commands = commands;
    }

    @Override
    public String getBotUsername() {
        return telegramConfig.getName();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();

            String display = null;
            if (message.hasText()) {
                String text = message.getText();
                String command = resolveCommand(text);


                if (command == null) display = "Команда не распознана";
                BotCommandProcessor botCommandProcessor = commands.get(command);
                if (botCommandProcessor != null) {
                    botCommandProcessor.processMessage(extractMessage(text, command), chatId);

                } else {
                    display = "Команда не распознана";
                    return;
                }


            }
            sendNotification(chatId, display);
        }

    }

    private String extractMessage(String message, String command) {
        return message.substring(command.length() + 1).trim();
    }

    private String resolveCommand(String command) {
        if (command.startsWith("/"))
            return command.substring(1);
        else return null;

    }

    public void sendNotification(Long chadId, String responseText) {
        SendMessage.builder()
                .chatId(chadId)
                .text(responseText)
                .build();
    }
}
