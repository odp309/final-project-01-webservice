package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.Branch;
import com.bni.finalproject01webservice.model.BranchReserve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BranchReserveRepository extends JpaRepository<BranchReserve, UUID> {

    BranchReserve findByBranchNameAndCurrencyCode(String name, String code);
}
