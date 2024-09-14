package com.chotchip.currencyRate.schema;

import com.chotchip.currencyRate.schema.valuts.CurrencyWrapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Currency {
    @JsonProperty("EUR")
    private CurrencyWrapper eur;
    @JsonProperty("USD")
    private CurrencyWrapper usd;
}
