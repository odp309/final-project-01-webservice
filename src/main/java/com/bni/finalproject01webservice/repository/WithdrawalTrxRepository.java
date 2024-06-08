package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.WithdrawalTrx;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WithdrawalTrxRepository extends JpaRepository<WithdrawalTrx, UUID> {

}
