package com.sparta.currency_user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ExchangeUpdateStatusDto {
    @NotBlank(message = "변경할 환전 상태를 입력하세요.")
    private String status;

    public ExchangeUpdateStatusDto(String status) {
        this.status = status;
    }

    public ExchangeUpdateStatusDto() {
    }
}
