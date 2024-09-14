package com.chotchip.processing.mapper;

import com.chotchip.processing.dto.NewAccountDTO;
import com.chotchip.processing.dto.PutAccountMoneyDTO;
import com.chotchip.processing.entity.AccountEntity;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {
    AccountEntity toEntity(NewAccountDTO newAccountDTO);


    NewAccountDTO toNewAccountDTO(AccountEntity accountEntity);

}
