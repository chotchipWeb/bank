package com.chotchip.Notification.service;

import com.chotchip.Notification.entity.AccountEvent;
import com.chotchip.Notification.entity.Operation;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountEventKafkaListener {
    private final SubscriptionService subscriptionService;
    private final TelegramSubscriptionServiceAgent telegramSubscriptionServiceAgent;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "account-events")
    @SneakyThrows
    public void consumerEvent(ConsumerRecord<Integer, String> consumerRecord) {
        Integer key = consumerRecord.key();
        String value = consumerRecord.value();

        AccountEvent accountEvent = objectMapper.readValue(value, AccountEvent.class);

        Long userId = subscriptionService.getSubscription((long) accountEvent.getUserId());

        if (userId != null) {
            String message = accountEvent.getOperation() == Operation.PUT ? formatPutEvent(accountEvent) : formatExchangeEvent(accountEvent);
            telegramSubscriptionServiceAgent.sendNotification(userId, message);
        }

    }

    private String formatPutEvent(AccountEvent accountEvent) {
        return "Счет " + accountEvent.getAccountId() + "date " + accountEvent.getCreated() + "Operation " + accountEvent.getOperation() + "in amount " + accountEvent.getAmount();
    }

    private String formatExchangeEvent(AccountEvent accountEvent) {
        return formatPutEvent(accountEvent);
    }
    //"Счет " + accountEvent.getAccountId() + "date " + accountEvent.getCreated() + "Operation " + accountEvent.getOperation() + "in amount " + accountEvent.getAmount() + " with account " + accountEvent. ;
}
