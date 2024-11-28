package com.sparta.currency_user.service;

import com.sparta.currency_user.dto.ExchangeRequestDto;
import com.sparta.currency_user.dto.ExchangeResponseDto;
import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.Exchange;
import com.sparta.currency_user.entity.User;
import com.sparta.currency_user.repository.CurrencyRepository;
import com.sparta.currency_user.repository.ExchangeRepository;
import com.sparta.currency_user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final ExchangeRepository exchangeRepository;
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;

    public ExchangeResponseDto createExchange(ExchangeRequestDto requestDto, Long sessionId){
        User findUser = userRepository.findById(sessionId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Currency findCurrency = currencyRepository.findByCurrencyName(requestDto.getCurrenyName());

        // 입력한 원화
        BigDecimal amountKrw = new BigDecimal(String.valueOf(requestDto.getMoney()));
        BigDecimal calExchange = amountKrw.divide(findCurrency.getExchangeRate(),2, RoundingMode.HALF_UP);

        Exchange createdExchange = new Exchange(findUser, findCurrency, requestDto.getMoney(), calExchange, "normal");

        Exchange savedExchange = exchangeRepository.save(createdExchange);

        return new ExchangeResponseDto(
                savedExchange.getId(),
                savedExchange.getUser().getId(),
                savedExchange.getCurrency().getId(),
                savedExchange.getAmountInKrw(),
                savedExchange.getAmountAfterExchange(),
                savedExchange.getStatus(),
                savedExchange.getCreatedAt(),
                savedExchange.getModifiedAt()
        );
    }

}
