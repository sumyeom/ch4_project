package com.sparta.currency_user.service;

import com.sparta.currency_user.dto.CurrencyRequestDto;
import com.sparta.currency_user.dto.CurrencyResponseDto;
import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.exception.CustomException;
import com.sparta.currency_user.exception.ErrorCode;
import com.sparta.currency_user.repository.CurrencyRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    @PostConstruct
    public void validateExchangeRate(){
        BigDecimal MIN_RATE = new BigDecimal("0.0001");
        BigDecimal MAX_RATE = new BigDecimal("1000000");
        List<Currency> currencies = currencyRepository.findAll();
        for(Currency cu : currencies){
            BigDecimal checkedExchangeRate = cu.getExchangeRate();
            if(checkedExchangeRate == null || checkedExchangeRate.compareTo(BigDecimal.ZERO) <= 0||
            checkedExchangeRate.compareTo(MIN_RATE) < 0 || checkedExchangeRate.compareTo(MAX_RATE) > 0 ){
                log.warn("{}번째 환율값 {}이 유효하지 않습니다.", cu.getId(), cu.getExchangeRate());
                throw new CustomException(ErrorCode.ILLEGAL_DATA);
            }
        }
    }

    public CurrencyResponseDto findById(Long id) {
        return new CurrencyResponseDto(findCurrencyById(id));
    }

    public Currency findCurrencyById(Long id) {
        return currencyRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.CURRENCY_NOT_FOUND));
    }

    public List<CurrencyResponseDto> findAll() {
        return currencyRepository.findAll().stream().map(CurrencyResponseDto::toDto).toList();
    }

    @Transactional
    public CurrencyResponseDto save(CurrencyRequestDto currencyRequestDto) {
        Currency savedCurrency = currencyRepository.save(currencyRequestDto.toEntity());
        return new CurrencyResponseDto(savedCurrency);
    }
}
