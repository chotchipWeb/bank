package com.chotchip.Notification.service.impl;

import com.chotchip.Notification.service.SubscriptionService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final ConcurrentHashMap<Long, Long> dataBase = new ConcurrentHashMap<>();

    @Override
    public void subscribeUser(Long userId, Long chatId) {
        dataBase.put(userId, chatId);
    }

    @Override
    public void unsubscribeUser(Long chatId) {
        Set<Map.Entry<Long, Long>> entries = dataBase.entrySet();
        for (Map.Entry<Long, Long> map : entries) {
            if (map.getValue().equals(chatId)) {
                Long mapKey = map.getKey();
                dataBase.remove(mapKey);
                return;
            }
        }

    }

    @Override
    public Long getSubscription(Long userId) {
        return dataBase.get(userId);
    }
}
