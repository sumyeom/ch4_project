package com.sparta.currency_user.dto;

import lombok.Getter;

@Getter
public class ExchangeUpdateStatusDto {
    private String status;

    public ExchangeUpdateStatusDto(String status) {
        this.status = status;
    }

    public ExchangeUpdateStatusDto() {
    }
}
