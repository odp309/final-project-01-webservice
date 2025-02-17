package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.BankAccount;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

    BankAccount findByAccountNumber(String accountNumber);

    List<BankAccount> findByUserId(UUID id, Sort sort);
}
