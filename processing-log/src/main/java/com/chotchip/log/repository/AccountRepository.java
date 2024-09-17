package com.chotchip.log.repository;

import com.chotchip.log.entity.AccountEvent;
import com.chotchip.log.entity.EventKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository  extends JpaRepository<AccountEvent, EventKey> {
}
