package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.BranchReserveLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BranchReserveLogRepository extends JpaRepository<BranchReserveLog, UUID> {

}
