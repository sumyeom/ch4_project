package com.sparta.currency_user.controller;

import com.sparta.currency_user.config.Const;
import com.sparta.currency_user.dto.ExchangeRequestDto;
import com.sparta.currency_user.dto.ExchangeResponseDto;
import com.sparta.currency_user.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exchanges")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping
    public ResponseEntity<ExchangeResponseDto> createExchange(
            @RequestBody ExchangeRequestDto requestDto,
            @SessionAttribute(name = Const.SESSION_KEY) Long sessionId
    ){
        ExchangeResponseDto exchangeResponseDto = exchangeService.createExchange(requestDto,sessionId);

        return new ResponseEntity<>(exchangeResponseDto, HttpStatus.OK);
    }

}
