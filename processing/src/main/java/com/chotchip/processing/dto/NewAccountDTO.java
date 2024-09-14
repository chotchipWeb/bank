package com.chotchip.processing.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class NewAccountDTO {

    @JsonAlias("user")
    private int userId;
    @JsonAlias("currency")
    private String currencyCode;

}
