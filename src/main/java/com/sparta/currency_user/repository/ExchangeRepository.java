package com.sparta.currency_user.repository;

import com.sparta.currency_user.dto.UserTotalInfoDto;
import com.sparta.currency_user.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
    List<Exchange> findAllByUserId(Long userId);

    @Query("select new com.sparta.currency_user.dto.UserTotalInfoDto(COUNT(ex.id), SUM(ex.amountInKrw)) from Exchange ex where ex.user.id = :userId group by ex.currency.currencyName")
    UserTotalInfoDto findUserTotalInfo(@Param("userId") Long userId);
}
