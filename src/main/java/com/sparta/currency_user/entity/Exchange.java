package com.sparta.currency_user.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Getter
@Entity
@Table(name = "exchange")
public class Exchange extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @Column(nullable = false)
    private BigDecimal amountInKrw;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amountAfterExchange;

    @Column(nullable = false, length = 10)
    private String status;

    public Exchange(User user, Currency currency, BigDecimal amountInKrw, BigDecimal amountAfterExchange, String status){
        this.user = user;
        this.currency = currency;
        this.amountInKrw = amountInKrw;
        this.amountAfterExchange = amountAfterExchange;
        this.status = status;
    }

    public Exchange() {
    }

    public void updateStatus(String status){
        this.status = status;
    }
}
