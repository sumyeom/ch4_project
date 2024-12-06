package com.sparta.currency_user.service;

import com.sparta.currency_user.dto.ExchangeRequestDto;
import com.sparta.currency_user.dto.ExchangeResponseDto;
import com.sparta.currency_user.dto.ExchangeUpdateStatusDto;
import com.sparta.currency_user.dto.UserTotalInfoDto;
import com.sparta.currency_user.entity.Currency;
import com.sparta.currency_user.entity.Exchange;
import com.sparta.currency_user.entity.User;
import com.sparta.currency_user.exception.CustomException;
import com.sparta.currency_user.exception.ErrorCode;
import com.sparta.currency_user.repository.CurrencyRepository;
import com.sparta.currency_user.repository.ExchangeRepository;
import com.sparta.currency_user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeService {
    private final ExchangeRepository exchangeRepository;
    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;

    /**
     * 환전 요청 처리 메서드
     * @param requestDto
     * @param sessionId
     * @return
     */
    @Transactional
    public ExchangeResponseDto createExchange(ExchangeRequestDto requestDto, Long sessionId){
        User findUser = userRepository.findById(sessionId)
                .orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));

        Currency findCurrency = currencyRepository.findByCurrencyName(requestDto.getCurrencyName());
        if(findCurrency == null){
            throw new CustomException(ErrorCode.CURRENCY_NOT_FOUND);
        }
        BigDecimal calExchange = selectCurrencySymbol(requestDto.getMoney(), findCurrency.getExchangeRate(),findCurrency.getCurrencyName());

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

    /**
     * 유저의 환전 요청 조회 메서드
     * @param userId
     * @return
     */
    public List<ExchangeResponseDto> findAllExchangesByUserId(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Exchange> findExchanges = exchangeRepository.findAllByUserId(userId);

        return findExchanges.stream().map(ExchangeResponseDto::toDto).toList();
    }

    /**
     * 환전 상태 변경 메서드
     * @param exchangeId
     * @param dto
     * @return
     */
    public ExchangeResponseDto updateExchangeStatus(Long exchangeId, ExchangeUpdateStatusDto dto) {
        Exchange findExchange = exchangeRepository.findById(exchangeId)
                .orElseThrow(()-> new CustomException(ErrorCode.EXCHANGE_NOT_FOUND));

        findExchange.updateStatus(dto.getStatus());
        Exchange updateExchange = exchangeRepository.save(findExchange);
        return new ExchangeResponseDto(
                updateExchange.getId(),
                updateExchange.getUser().getId(),
                updateExchange.getCurrency().getId(),
                updateExchange.getAmountInKrw(),
                updateExchange.getAmountAfterExchange(),
                updateExchange.getStatus(),
                updateExchange.getCreatedAt(),
                updateExchange.getModifiedAt()
        );
    }

    /**
     * 특정 유저 환전 요청 총합 정보 메서드
     * @param userId
     * @return
     */
    public UserTotalInfoDto findTotalExchangeInfo(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return exchangeRepository.findUserTotalInfo(userId);
    }

    /**
     * 통화 선택 후 계산 하는 메서드
     */
    private BigDecimal selectCurrencySymbol(BigDecimal fromAmount, BigDecimal exchangeRate, String currencyName){
        BigDecimal calAmount;
        switch (currencyName){
            case "USD", "EUR", "COP" :
                calAmount = fromAmount.divide(exchangeRate,2, RoundingMode.HALF_UP);
                break;
            case "AUD" :
                calAmount = fromAmount.divide(exchangeRate,1, RoundingMode.HALF_UP);
                break;
            case "JPY":
                calAmount = fromAmount.divide(exchangeRate,0, RoundingMode.HALF_UP);
                break;
            default:
                throw new CustomException(ErrorCode.ILLEGAL_DATA);
        }

        return calAmount;
    }
}
