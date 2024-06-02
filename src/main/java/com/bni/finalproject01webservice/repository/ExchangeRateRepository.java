package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, UUID> {

    @Query("""
            select
                e
            from
                ExchangeRate e
            where
                e.currency.code = :code
            order by
                e.createdAt desc
            limit 1
            """)
    ExchangeRate findExchangeRate(@Param("code") String code);
}

