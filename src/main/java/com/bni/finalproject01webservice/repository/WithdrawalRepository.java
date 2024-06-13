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

    Withdrawal findByReservationCode(String reservationCode);


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

    @Query("""
            SELECT 
            wd.id,
            w.amount, 
            w.createdAt, 
            w.status, 
            w.wallet.currency.code, 
            wd.trxType.name 
            FROM WithdrawalDetail wd 
            JOIN wd.withdrawal w 
            WHERE w.wallet.id = :walletId
            """)
    List<Object[]> findBySelectedWalletId (@Param("walletId") UUID walletId);

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