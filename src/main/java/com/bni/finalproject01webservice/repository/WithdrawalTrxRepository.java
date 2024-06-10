package com.bni.finalproject01webservice.repository;


import com.bni.finalproject01webservice.model.WithdrawalTrx;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WithdrawalTrxRepository extends JpaRepository<WithdrawalTrx, UUID> {

    List<WithdrawalTrx> findByBranchName (String name);

}
