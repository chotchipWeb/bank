package com.chotchip.log.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class EventKey implements Serializable {
    int accountId;
    String uuid;
}
