package com.chotchip.currencyRate.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class POJO {
    //    @JsonProperty("Date")
//    private Date date;
//    @JsonProperty("PreviousDate")
//    private Date previousDate;
//    @JsonProperty("PreviousURL")
//    private String previousURL;
//    @JsonProperty("Timestamp")
//    private Date timestamp;
    @JsonProperty("Valute")
    private Currency currency;
}
