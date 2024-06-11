package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, String> {

    @Query("""
            select
            	b
            from
            	Branch b
            left join
                b.branchReserves br
            where
                br.balance = :amountToWithdraw
                and br.currency.code = :currencyCode
            order by
            	(6371 * acos(cos(radians(:latitude)) * cos(radians(b.latitude)) * cos(radians(b.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(b.latitude))))
            """)
    List<Branch> findAllBranchOrderByDistance(@Param("latitude") double latitude,
                                              @Param("longitude") double longitude,
                                              @Param("amountToWithdraw") BigDecimal amountToWithdraw,
                                              @Param("currencyCode") String currencyCode);

    @Query("""
            select
            	b
            from
            	Branch b
            left join
                b.branchReserves br
            where
                b.code = :branchCode
                and br.balance >= :amountToWithdraw
                and br.currency.code = :currencyCode
            """)
    Branch findBranchWithValidation(@Param("branchCode") String branchCode,
                                    @Param("amountToWithdraw") BigDecimal amountToWithdraw,
                                    @Param("currencyCode") String currencyCode);
}