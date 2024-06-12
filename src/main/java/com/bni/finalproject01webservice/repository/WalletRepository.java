package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

   List<Wallet> findByBankAccountAccountNumber(String accountNumber);

   Wallet findByBankAccountAccountNumberAndCurrencyCode(String accountNumber, String code);

   @Modifying
   @Transactional
   @Query("""
            update
                Wallet w
            set
                w.balance = :balance,
                w.updatedAt = current_timestamp
            where
                w.id = :id
            """)
   void updateWalletBalance(@Param("id") UUID id, @Param("balance") BigDecimal balance);
}
