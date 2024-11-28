package com.sparta.currency_user.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UserTotalInfoDto {
    private final Long totalCount;
    private final BigDecimal totalAmount;

    public UserTotalInfoDto(Long totalCount, BigDecimal totalAmount) {
        this.totalCount = totalCount;
        this.totalAmount = totalAmount;
    }
}
