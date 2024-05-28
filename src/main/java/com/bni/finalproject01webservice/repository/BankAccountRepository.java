package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

    BankAccount findByAccountNumber(String accountNumber);
}
