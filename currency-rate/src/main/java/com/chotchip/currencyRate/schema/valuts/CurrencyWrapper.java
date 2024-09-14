package com.chotchip.currencyRate.schema.valuts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyWrapper {
    //    @JsonProperty("ID")
//    public String id;
//    @JsonProperty("NumCode")
//    public String numCode;
    @JsonProperty("CharCode")
    public String charCode;
    //    @JsonProperty("Nominal")
//    public int nominal;
//    @JsonProperty("Name")
//    public String name;
    @JsonProperty("Value")
    public BigDecimal value;
//    @JsonProperty("Previous")
//    public double previous;
}
