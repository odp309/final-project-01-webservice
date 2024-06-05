package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.TransactionValas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionValasRepository extends JpaRepository<TransactionValas, UUID> {

}
