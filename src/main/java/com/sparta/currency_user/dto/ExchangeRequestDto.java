package com.sparta.currency_user.dto;

import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.Exchange;
import com.sparta.currency_user.entity.User;
import lombok.Getter;

@Getter
public class ExchangeRequestDto {
    private final String currenyName;
    private final Long money;

    public ExchangeRequestDto(String currenyName, Long money){
        this.currenyName = currenyName;
        this.money = money;
    }

}
