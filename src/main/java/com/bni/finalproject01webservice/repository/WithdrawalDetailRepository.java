package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.WithdrawalDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WithdrawalDetailRepository extends JpaRepository<WithdrawalDetail, UUID> {

}
