package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.TrxType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrxTypeRepository extends JpaRepository<TrxType, Long> {

    TrxType findByName(String name);
}
