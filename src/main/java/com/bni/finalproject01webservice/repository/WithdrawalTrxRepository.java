package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.WithdrawalTrx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface WithdrawalTrxRepository extends JpaRepository<WithdrawalTrx, UUID> {

    List<WithdrawalTrx> findByBranchCode (String code);

    WithdrawalTrx findByReservationNumber(String reservationNumber);

    List<WithdrawalTrx> findByWalletId (UUID walletId);

    @Query("""
            select
            	wt
            from
            	WithdrawalTrx wt
            where
            	DATE(wt.reservationDate) = :today
            	and wt.status = 'Terjadwal'
            """)
    List<WithdrawalTrx> findScheduledWithdrawalForToday(@Param("today") LocalDate today);

    @Modifying
    @Transactional
    @Query("""
            update
            	WithdrawalTrx wt
            set
            	wt.status = 'Kadaluarsa'
            where
            	wt.id = :id
            """)
    void updateWithdrawalTrxStatusToExpired(@Param("id") UUID id);
}