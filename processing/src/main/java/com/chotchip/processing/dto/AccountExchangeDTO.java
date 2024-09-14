package com.chotchip.processing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data

public class AccountExchangeDTO {

    private int fromAccount;
    private int toAccount;
    private BigDecimal amount;
}
