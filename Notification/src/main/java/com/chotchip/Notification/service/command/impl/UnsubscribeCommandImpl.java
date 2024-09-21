package com.chotchip.Notification.service.command.impl;

import com.chotchip.Notification.service.SubscriptionService;
import com.chotchip.Notification.service.command.BotCommandProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("unsubscribe")
@RequiredArgsConstructor
public class UnsubscribeCommandImpl implements BotCommandProcessor {
    private final SubscriptionService subscriptionService;

    @Override
    public String processMessage(String message, Long chadId) {
        subscriptionService.unsubscribeUser(chadId);
        return "Подписка отменена";
    }
}
