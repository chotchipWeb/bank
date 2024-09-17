package com.chotchip.log.service;

import com.chotchip.log.entity.AccountEvent;
import com.chotchip.log.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountEventKafkaListener {
    private final AccountRepository accountRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "account-events")
    @SneakyThrows
    public void consumeEvent(ConsumerRecord<Integer, String> record) {
        var key = record.key();
        var value = record.value();
        AccountEvent accountEvent = objectMapper.readValue(value, AccountEvent.class);
        accountRepository.save(accountEvent);
    }
}
