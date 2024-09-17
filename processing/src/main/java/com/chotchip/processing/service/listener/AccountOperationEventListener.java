package com.chotchip.processing.service.listener;

import com.chotchip.processing.event.AccountEvent;
import com.chotchip.processing.event.Operation;
import com.chotchip.processing.service.AccountEventSendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AccountOperationEventListener {
    private final AccountEventSendingService accountEventSendingService;

    @TransactionalEventListener
    public void handleEvent(AccountEvent accountEvent) {
        accountEventSendingService.sendEvent(accountEvent);
    }

}
