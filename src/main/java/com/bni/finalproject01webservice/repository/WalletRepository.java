package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

   List<Wallet> findByBankAccountAccountNumber(String accountNumber);
   Wallet findByBankAccountAccountNumberAndCurrencyCode(String accountNumber, String code);
}
