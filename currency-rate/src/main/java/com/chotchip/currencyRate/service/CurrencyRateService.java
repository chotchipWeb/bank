package com.chotchip.currencyRate.service;

import com.chotchip.currencyRate.schema.Currency;
import com.chotchip.currencyRate.schema.POJO;
import com.chotchip.currencyRate.schema.valuts.CurrencyWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CurrencyRateService {
    private final ObjectMapper objectMapper;

    @Autowired
    public CurrencyRateService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public POJO jsonInObject() {
        var url = "https://www.cbr-xml-daily.ru/daily_json.js";
        var client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());

        String body = send.body();
        POJO pojo2 = objectMapper.readValue(body, POJO.class);
        return pojo2;
    }


    public BigDecimal getCurrencyByCode(String code) {
        POJO pojo = jsonInObject();
        String codeLower = code.toLowerCase();
        Currency currency = pojo.getCurrency();
        CurrencyWrapper EUR = currency.getEur();
        CurrencyWrapper USD = currency.getUsd();
        String codeUSD = USD.getCharCode().toLowerCase();
        return codeLower.equals(codeUSD) ? USD.getValue() : EUR.getValue();
    }
}
