package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, String> {

    Currency findByCode(String code);

    @Query("SELECT c.code FROM Currency c")
    List<String> findAllCurrencyCode();
}
