package com.sparta.currency_user.dto;

import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.Exchange;
import com.sparta.currency_user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ExchangeRequestDto {
    @NotBlank(message = "통화 이름을 입력하세요.")
    private final String currenyName;

    @NotNull(message = "환전할 금액을 입력해주세요.")
    private final Long money;

    public ExchangeRequestDto(String currenyName, Long money){
        this.currenyName = currenyName;
        this.money = money;
    }

}
