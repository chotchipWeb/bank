package com.chotchip.Notification.service;

public interface SubscriptionService {

    void subscribeUser(Long userId, Long chatId);

    void unsubscribeUser(Long chatId);

    Long getSubscription(Long userId);


}
