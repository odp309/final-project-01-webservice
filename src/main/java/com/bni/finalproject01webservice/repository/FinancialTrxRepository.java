package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.FinancialTrx;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FinancialTrxRepository extends JpaRepository<FinancialTrx, UUID> {

}
