package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {

    TransactionType findByName (String name);
}
