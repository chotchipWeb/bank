package com.chotchip.Notification.service.command.impl;

import com.chotchip.Notification.service.AuthService;
import com.chotchip.Notification.service.ResolveUserService;
import com.chotchip.Notification.service.SubscriptionService;
import com.chotchip.Notification.service.command.BotCommandProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("subscribe")
@RequiredArgsConstructor
public class SubscribeCommandImpl implements BotCommandProcessor {
    private final AuthService authService;
    private final ResolveUserService resolveUserService;
    private final SubscriptionService subscriptionService;

    @Override
    public String processMessage(String message, Long chadId) {
        String[] split = message.split(":");
        String user = split[0];
        String password = split[1];

        String token = authService.authUser(user, password);
        Long userId = (long) resolveUserService.resolveUserId(token);

        if (userId != null) {
            subscriptionService.subscribeUser(userId, chadId);
            return "Подписка оформлена";
        } else return "Подписка не оформлена " + user + "не найден";
    }
}
