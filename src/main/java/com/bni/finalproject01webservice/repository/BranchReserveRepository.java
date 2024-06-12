package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.BranchReserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface BranchReserveRepository extends JpaRepository<BranchReserve, UUID> {

    BranchReserve findByBranchCodeAndCurrencyCode(String branchCode, String currencyCode);

    List<BranchReserve> findByBranchCode(String code);

    @Modifying
    @Transactional
    @Query("""
            update
                BranchReserve br
            set
                br.balance = :balance,
                br.updatedAt = current_timestamp
            where
                br.id = :id
            """)
    void updateBranchReserveBalance(@Param("id") UUID id, @Param("balance") BigDecimal balance);
}
