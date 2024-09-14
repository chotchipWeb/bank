package com.chotchip.processing.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PutAccountMoneyDTO {

    @JsonAlias(value = "uuid")
    private String uuid;

    @JsonAlias(value = "id")
    private int id;

    @JsonAlias(value = "amount")
    private BigDecimal amount;

}
