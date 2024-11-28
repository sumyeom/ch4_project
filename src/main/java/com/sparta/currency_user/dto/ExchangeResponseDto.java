package com.sparta.currency_user.dto;

import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.Exchange;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class ExchangeResponseDto {
    private final Long id;
    private final Long userId;
    private final Long toCurrencyId;
    private final Long amountInKrw;
    private final BigDecimal amountAfterExchange;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public ExchangeResponseDto(Long id, Long userId, Long toCurrencyId, Long amountInKrw, BigDecimal amountAfterExchange, String status, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.toCurrencyId = toCurrencyId;
        this.amountInKrw = amountInKrw;
        this.amountAfterExchange = amountAfterExchange;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ExchangeResponseDto toDto(Exchange exchange) {
        return new ExchangeResponseDto(
                exchange.getId(),
                exchange.getUser().getId(),
                exchange.getCurrency().getId(),
                exchange.getAmountInKrw(),
                exchange.getAmountAfterExchange(),
                exchange.getStatus(),
                exchange.getCreatedAt(),
                exchange.getModifiedAt()
        );
    }
}
