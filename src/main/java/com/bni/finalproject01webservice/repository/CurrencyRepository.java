package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, String> {

    Currency findByCode(String code);
}
