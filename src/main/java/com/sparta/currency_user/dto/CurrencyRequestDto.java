package com.sparta.currency_user.dto;

import com.sparta.currency_user.entity.Currency;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CurrencyRequestDto {
    @NotBlank(message = "통화 이름을 입력하세요.")
    private String currencyName;

    @NotNull(message = "환율을 입력하세요.")
    @DecimalMin(value = "0.0001")
    @DecimalMax(value = "1000000000000")
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
