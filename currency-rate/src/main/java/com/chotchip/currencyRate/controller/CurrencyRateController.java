package com.chotchip.currencyRate.controller;

import com.chotchip.currencyRate.schema.POJO;
import com.chotchip.currencyRate.schema.valuts.CurrencyWrapper;
import com.chotchip.currencyRate.service.CurrencyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/currency-rate")
public class CurrencyRateController {
    private final CurrencyRateService currencyRateService;

    @Autowired
    public CurrencyRateController(CurrencyRateService currencyRateService) {
        this.currencyRateService = currencyRateService;
    }

    @GetMapping()
    public POJO getAllCurrencyRate() {
        return currencyRateService.jsonInObject();
    }

    @GetMapping("/{code}")
    public BigDecimal getCurrencyByCode(@PathVariable("code") String code) {
        return currencyRateService.getCurrencyByCode(code);
    }
}
