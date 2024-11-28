package com.sparta.currency_user.controller;

import com.sparta.currency_user.config.Const;
import com.sparta.currency_user.dto.ExchangeRequestDto;
import com.sparta.currency_user.dto.ExchangeResponseDto;
import com.sparta.currency_user.dto.ExchangeUpdateStatusDto;
import com.sparta.currency_user.dto.UserTotalInfoDto;
import com.sparta.currency_user.service.ExchangeService;
import com.sparta.currency_user.util.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchanges")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping
    public ResponseEntity<ExchangeResponseDto> createExchange(
            @Valid @RequestBody ExchangeRequestDto requestDto,
            @SessionAttribute(name = Const.SESSION_KEY) Long sessionId,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            ValidationUtils.bindErrorMessage(bindingResult);
        }

        ExchangeResponseDto exchangeResponseDto = exchangeService.createExchange(requestDto,sessionId);

        return new ResponseEntity<>(exchangeResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ExchangeResponseDto>> findAllExchangesByUserId(
            @PathVariable Long userId
    ){
        List<ExchangeResponseDto> exchangeResponseDtos = exchangeService.findAllExchangesByUserId(userId);

        return new ResponseEntity<>(exchangeResponseDtos, HttpStatus.OK);
    }

    @GetMapping("/{userId}/totals")
    public ResponseEntity<UserTotalInfoDto> findTotalExchangeInfo(
            @PathVariable Long userId
    ){
        UserTotalInfoDto userTotalInfoDto = exchangeService.findTotalExchangeInfo(userId);

        return new ResponseEntity<>(userTotalInfoDto, HttpStatus.OK);
    }

    @PatchMapping({"/{exchangeId}"})
    public ResponseEntity<ExchangeResponseDto> updateExchangeStatus(
            @PathVariable Long exchangeId,
            @Valid @RequestBody ExchangeUpdateStatusDto dto,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            ValidationUtils.bindErrorMessage(bindingResult);
        }

        ExchangeResponseDto exchangeResponseDto = exchangeService.updateExchangeStatus(exchangeId, dto);

        return new ResponseEntity<>(exchangeResponseDto,HttpStatus.OK);
    }


}
