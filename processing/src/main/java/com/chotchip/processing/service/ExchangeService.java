package com.chotchip.processing.service;

import com.chotchip.processing.dto.AccountExchangeDTO;
import com.chotchip.processing.dto.PutAccountMoneyDTO;
import com.chotchip.processing.entity.AccountEntity;
import com.chotchip.processing.exception.AccountFlawBalanceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final CurrencyService currencyService;
    private final AccountService accountService;
    private static final String CURRENTCURRENCY = "RUB";
    private static final String CURRENCYEUR = "EUR";
    private static final String CURRENCYUSD = "USD";

    @Transactional()
    public List<AccountEntity> exchangeCurrency(String uuid, AccountExchangeDTO accountExchangeDTO) {
        AccountEntity fromAccount = accountService.findById(accountExchangeDTO.getFromAccount());
        AccountEntity toAccount = accountService.findById(accountExchangeDTO.getToAccount());

        BigDecimal amount = accountExchangeDTO.getAmount();
        if (amount.subtract(fromAccount.getBalance()).compareTo(new BigDecimal(0)) >= 0)
            throw new AccountFlawBalanceException();
        List<AccountEntity> listAcc = null;
        if (CURRENTCURRENCY.equals(fromAccount.getCurrencyCode()) && CURRENTCURRENCY.equals(toAccount.getCurrencyCode())) {
            listAcc = exchangeEqualsCurrency(uuid, fromAccount, toAccount, amount);
        } else if (CURRENTCURRENCY.equals(fromAccount.getCurrencyCode()) && (CURRENCYEUR.equals(toAccount.getCurrencyCode()) || CURRENCYUSD.equals(toAccount.getCurrencyCode()))) {
            listAcc = multiExchangeOfRUBToEUROrUSD(uuid, toAccount, amount, fromAccount);
        } else if ((CURRENCYUSD.equals(fromAccount.getCurrencyCode()) || CURRENCYEUR.equals(fromAccount.getCurrencyCode())) && CURRENTCURRENCY.equals(toAccount.getCurrencyCode())) {
            listAcc = multiExchangeOfUSDOrEURToRUB(uuid, fromAccount, amount, toAccount);
        } else if (CURRENCYUSD.equals(fromAccount.getCurrencyCode()) && CURRENCYUSD.equals(toAccount.getCurrencyCode())) {
            listAcc = exchangeEqualsCurrencyUSDOrEUR(uuid, fromAccount, toAccount, amount);
        } else if (CURRENCYEUR.equals(fromAccount.getCurrencyCode()) && CURRENCYEUR.equals(toAccount.getCurrencyCode())) {
            listAcc = exchangeEqualsCurrencyUSDOrEUR(uuid, fromAccount, toAccount, amount);
        } else if ((CURRENCYEUR.equals(fromAccount.getCurrencyCode()) || CURRENCYUSD.equals(fromAccount.getCurrencyCode())) && (CURRENCYEUR.equals(toAccount.getCurrencyCode()) || CURRENCYUSD.equals(toAccount.getCurrencyCode()))) {
            BigDecimal moneySubtract = subtractOfUSDOrEURToRUBDecimal(uuid, amount, fromAccount);
            listAcc = replenishmentOfUSDOrEURToRUBDecimal(uuid, toAccount, moneySubtract, fromAccount);
        }

        return listAcc;
    }

    private List<AccountEntity> exchangeEqualsCurrencyUSDOrEUR(String uuid, AccountEntity fromAccount, AccountEntity toAccount, BigDecimal amount) {
        AccountEntity acc1 = null;
        AccountEntity acc2 = null;
        if (CURRENCYUSD.equals(fromAccount.getCurrencyCode()) && CURRENCYUSD.equals(toAccount.getCurrencyCode())) {
            acc1 = accountService.replenishmentAccount(uuid, new PutAccountMoneyDTO(uuid, fromAccount.getId(), amount.negate()));
            acc2 = accountService.replenishmentAccount(uuid, new PutAccountMoneyDTO(uuid, toAccount.getId(), amount));
        } else {
            acc1 = accountService.replenishmentAccount(uuid, new PutAccountMoneyDTO(uuid, fromAccount.getId(), amount.negate()));
            acc2 = accountService.replenishmentAccount(uuid, new PutAccountMoneyDTO(uuid, toAccount.getId(), amount));
        }
        return List.of(acc1, acc2);
    }

    private List<AccountEntity> multiExchangeOfRUBToEUROrUSD(String uuid, AccountEntity toAccount, BigDecimal amount, AccountEntity fromAccount) {
        AccountEntity acc1 = null;
        AccountEntity acc2 = null;
        if (CURRENCYEUR.equals(toAccount.getCurrencyCode())) {
            BigDecimal rate = currencyService.loadCurrencyRate(CURRENCYEUR);
            BigDecimal money = amount.divide(rate, 4, RoundingMode.HALF_DOWN);
            acc1 = accountService.replenishmentAccount(uuid, new PutAccountMoneyDTO(uuid, fromAccount.getId(), amount.negate()));
            acc2 = accountService.replenishmentAccount(uuid, new PutAccountMoneyDTO(uuid, toAccount.getId(), money));
        } else if (CURRENCYUSD.equals(toAccount.getCurrencyCode())) {
            BigDecimal rate = currencyService.loadCurrencyRate(CURRENCYUSD);
            BigDecimal money = amount.divide(rate, 4, RoundingMode.HALF_DOWN);
            acc1 = accountService.replenishmentAccount(uuid, new PutAccountMoneyDTO(uuid, fromAccount.getId(), amount.negate()));
            acc2 = accountService.replenishmentAccount(uuid, new PutAccountMoneyDTO(uuid, toAccount.getId(), money));
        }

        return List.of(acc1, acc2);
    }


    private List<AccountEntity> multiExchangeOfUSDOrEURToRUB(String uuid, AccountEntity toAccount, BigDecimal amount, AccountEntity fromAccount) {
        AccountEntity acc1 = null;
        AccountEntity acc2 = null;
        if (CURRENCYEUR.equals(toAccount.getCurrencyCode())) {
            BigDecimal rate = currencyService.loadCurrencyRate(CURRENCYEUR);
            BigDecimal money = amount.multiply(rate, new MathContext(4));
            acc1 = accountService.replenishmentAccount(uuid, new PutAccountMoneyDTO(uuid, toAccount.getId(), amount.negate()));
            acc2 = accountService.replenishmentAccount(uuid, new PutAccountMoneyDTO(uuid, fromAccount.getId(), money));
        } else if (CURRENCYUSD.equals(toAccount.getCurrencyCode())) {
            BigDecimal rate = currencyService.loadCurrencyRate(CURRENCYUSD);
            BigDecimal money = amount.multiply(rate, new MathContext(4));
            acc1 = accountService.replenishmentAccount(uuid, new PutAccountMoneyDTO(uuid, toAccount.getId(), amount.negate()));
            acc2 = accountService.replenishmentAccount(uuid, new PutAccountMoneyDTO(uuid, fromAccount.getId(), money));
        }
        return List.of(acc1, acc2);
    }


    private BigDecimal subtractOfUSDOrEURToRUBDecimal(String uuid, BigDecimal amount, AccountEntity fromAccount) {
        AccountEntity acc1;
        BigDecimal money = null;
        if (CURRENCYEUR.equals(fromAccount.getCurrencyCode())) {

            accountService.replenishmentAccount(uuid, new PutAccountMoneyDTO(uuid, fromAccount.getId(), amount.negate()));
            BigDecimal rate = currencyService.loadCurrencyRate(CURRENCYEUR);
            money = amount.multiply(rate, new MathContext(4));
        } else if (CURRENCYUSD.equals(fromAccount.getCurrencyCode())) {
            accountService.replenishmentAccount(uuid, new PutAccountMoneyDTO(uuid, fromAccount.getId(), amount.negate()));
            BigDecimal rate = getRate();
            money = amount.multiply(rate, new MathContext(4));
        }
        return money;
    }

    private BigDecimal getRate() {
        return currencyService.loadCurrencyRate(CURRENCYUSD);
    }

    private List<AccountEntity> replenishmentOfUSDOrEURToRUBDecimal(String uuid, AccountEntity toAccount, BigDecimal amount, AccountEntity fromAccount) {
        AccountEntity acc1 = null;

        BigDecimal money = null;
        if (CURRENCYEUR.equals(toAccount.getCurrencyCode())) {
            BigDecimal rate = currencyService.loadCurrencyRate(CURRENCYEUR);
            money = amount.divide(rate, 4, RoundingMode.HALF_DOWN);
            acc1 = accountService.replenishmentAccount(uuid, new PutAccountMoneyDTO(uuid, toAccount.getId(), money));

        } else if (CURRENCYUSD.equals(toAccount.getCurrencyCode())) {
            BigDecimal rate = currencyService.loadCurrencyRate(CURRENCYUSD);
            money = amount.divide(rate, 4, RoundingMode.HALF_DOWN);
            acc1 = accountService.replenishmentAccount(uuid, new PutAccountMoneyDTO(uuid, toAccount.getId(), money));

        }
        return List.of(fromAccount, acc1);
    }

    private List<AccountEntity> exchangeEqualsCurrency(String uuid, AccountEntity fromAccount, AccountEntity toAccount, BigDecimal amount) {
        var acc1 = accountService.replenishmentAccount(uuid, new PutAccountMoneyDTO(uuid, fromAccount.getId(), amount.negate()));
        var acc2 = accountService.replenishmentAccount(uuid, new PutAccountMoneyDTO(uuid, toAccount.getId(), amount));
        return List.of(acc1, acc2);
    }


}
