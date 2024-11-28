package com.sparta.currency_user.dto;

import com.sparta.currency_user.entity.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CurrencyRequestDto {
    @NotBlank(message = "통화 이름을 입력하세요.")
    private String currencyName;

    @NotNull(message = "환율을 입력하세요.")
    private BigDecimal exchangeRate;

    @NotBlank(message = "기호를 입력하세요.")
    private String symbol;

    public Currency toEntity() {
        return new Currency(
                this.currencyName,
                this.exchangeRate,
                this.symbol
        );
    }
}
