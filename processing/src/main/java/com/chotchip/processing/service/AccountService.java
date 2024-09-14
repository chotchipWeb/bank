package com.chotchip.processing.service;

import com.chotchip.processing.dto.NewAccountDTO;
import com.chotchip.processing.dto.PutAccountMoneyDTO;
import com.chotchip.processing.entity.AccountEntity;
import com.chotchip.processing.exception.AccountNotEqualsUUIDException;
import com.chotchip.processing.exception.AccountNotFoundException;
import com.chotchip.processing.mapper.AccountMapper;
import com.chotchip.processing.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional
    public AccountEntity createAccount(NewAccountDTO newAccountDTO) {
        var entity = accountMapper.toEntity(newAccountDTO);
        entity.setBalance(new BigDecimal(0));
        return accountRepository.save(entity);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public AccountEntity replenishmentAccount(String uuid, PutAccountMoneyDTO putAccountMoneyDTO) {
        String uuidDTO = putAccountMoneyDTO.getUuid();
        if (!uuid.equals(uuidDTO)) throw new AccountNotEqualsUUIDException();
        Optional<AccountEntity> accountEntity = accountRepository.findById(putAccountMoneyDTO.getId());
        var result = accountEntity
                .map(account -> {
                    var balance = account.getBalance().add(putAccountMoneyDTO.getAmount());
                    account.setBalance(balance);
                    return accountRepository.save(account);
                })
                .orElseThrow(AccountNotFoundException::new);
        return result;
    }

    @Transactional(readOnly = true)
    public AccountEntity findById(int id) {
        return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<AccountEntity> findAll() {
        return accountRepository.findAll();
    }

}
