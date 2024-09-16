package com.chotchip.processing.service;

import com.chotchip.processing.event.AccountEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountEventSendingService {
    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    public final static String TOPIC = "account-events";

    @SneakyThrows
    public void sendEvent(AccountEvent accountEvent) {
        var accountId = accountEvent.getAccountId();
        String message;
        message = objectMapper.writeValueAsString(accountEvent);
        var future = kafkaTemplate.send(TOPIC, accountId, message);
        future.get();
    }
}
