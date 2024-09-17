package com.chotchip.log.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@IdClass(EventKey.class)
@Entity(name = "account_event")
public class AccountEvent {
    @Id
    @Column(name = "uuid", nullable = false)
    String uuid;

    @Column(name = "user_id", nullable = false)
    int userId;
    @Id
    @Column(name = "account_id", nullable = false)
    int accountId;
    @Column(name = "operation", nullable = false)
    Operation operation;
    @Column(name = "amount", nullable = false)
    BigDecimal amount;
    @Column(name = "created", nullable = false)
    Date created;
}