package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.model.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, UUID> {

    List<Withdrawal> findByBranchCode (String code);

    Withdrawal findByReservationNumber(String reservationNumber);

    List<WithdrawalTrx> findByWalletId (UUID walletId);

    @Query("""
            select
            	wt
            from
            	Withdrawal wt
            where
            	DATE(wt.reservationDate) = :today
            	and wt.status = 'Terjadwal'
            """)
    List<Withdrawal> findScheduledWithdrawalForToday(@Param("today") LocalDate today);

    @Modifying
    @Transactional
    @Query("""
            update
            	Withdrawal wt
            set
            	wt.status = 'Kadaluarsa',
            	wt.updatedAt = current_timestamp
            where
            	wt.id = :id
            """)
    void updateWithdrawalStatusToExpired(@Param("id") UUID id);
}