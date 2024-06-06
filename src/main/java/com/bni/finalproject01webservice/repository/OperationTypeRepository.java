package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {

    OperationType findByName(String name);
}
