package com.sparta.currency_user.controller;

import com.sparta.currency_user.dto.CurrencyRequestDto;
import com.sparta.currency_user.dto.CurrencyResponseDto;
import com.sparta.currency_user.service.CurrencyService;
import com.sparta.currency_user.util.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currencies")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<List<CurrencyResponseDto>> findCurrencies() {
        return ResponseEntity.ok().body(currencyService.findAll());
    }

    @GetMapping("/{currencyId}")
    public ResponseEntity<CurrencyResponseDto> findCurrency(@PathVariable Long currencyId) {
        return ResponseEntity.ok().body(currencyService.findById(currencyId));
    }

    @PostMapping
    public ResponseEntity<CurrencyResponseDto> createCurrency(
            @Valid @RequestBody CurrencyRequestDto currencyRequestDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            ValidationUtils.bindErrorMessage(bindingResult);
        }
        
        return ResponseEntity.ok().body(currencyService.save(currencyRequestDto));
    }
}
