package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.TrxHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrxHistoryRepository extends JpaRepository<TrxHistory, UUID> {

}
