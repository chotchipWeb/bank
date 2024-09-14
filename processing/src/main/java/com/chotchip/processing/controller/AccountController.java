package com.chotchip.processing.controller;

import com.chotchip.processing.dto.AccountExchangeDTO;
import com.chotchip.processing.dto.NewAccountDTO;
import com.chotchip.processing.dto.PutAccountMoneyDTO;
import com.chotchip.processing.entity.AccountEntity;
import com.chotchip.processing.exception.AccountWrapperException;
import com.chotchip.processing.exception.response.AccountErrorResponse;
import com.chotchip.processing.service.AccountService;
import com.chotchip.processing.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/processing")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final ExchangeService exchangeService;

    @PostMapping("/createAccount")
    public AccountEntity createAccount(@RequestBody NewAccountDTO newAccountDTO) {
        return accountService.createAccount(newAccountDTO);
    }

    @PutMapping("/replenishmentAccount/{uuid}")
    public AccountEntity replenishmentAccount(@PathVariable("uuid") String uuid,
                                              @RequestBody PutAccountMoneyDTO putAccountMoneyDTO) {
        return accountService.replenishmentAccount(uuid, putAccountMoneyDTO);

    }

    @GetMapping("/account/{id}")
    public AccountEntity findByAccount(@PathVariable("id") int id) {
        return accountService.findById(id);
    }


    @PutMapping("/account/exchangeCurrency/{uuid}")
    public List<AccountEntity> exchangeCurrency(@PathVariable("uuid") String uuid, @RequestBody AccountExchangeDTO accountExchangeDTO) {
        return exchangeService.exchangeCurrency(uuid, accountExchangeDTO);
    }

    @GetMapping("/accountAll")
    public List<AccountEntity> findAllAccount() {
        return accountService.findAll();
    }

    @ExceptionHandler
    public ResponseEntity<AccountErrorResponse> accountErrorHandler(AccountWrapperException e) {
        return new ResponseEntity<>(new AccountErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
