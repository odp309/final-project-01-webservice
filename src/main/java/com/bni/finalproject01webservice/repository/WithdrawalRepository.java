package com.bni.finalproject01webservice.repository;

import com.bni.finalproject01webservice.dto.withdrawal.response.RecapWithdrawalResponseDTO;
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

    List<Withdrawal> findByBranchCode(String code);

    Withdrawal findByReservationCode(String reservationCode);

    Withdrawal findByUserIdAndStatus(UUID userId, String status);

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
            select
            	new com.bni.finalproject01webservice.dto.withdrawal.response.RecapWithdrawalResponseDTO(
                    SUM(w.amount),
            	    function('TO_DATE', function('TO_CHAR', w.reservationDate, 'YYYY-MM'), 'YYYY-MM'),
            	    w.wallet.currency.code)
            from
            	Withdrawal w
            where
            	w.branch.code = :branchCode
            	and w.status = 'Sukses'
            group by
            	function('TO_CHAR', w.reservationDate, 'YYYY-MM'),
            	w.wallet.currency.code
    """)
    List<RecapWithdrawalResponseDTO> getSumOfAmountGroupedByCurrencyAndMonth(@Param("branchCode") String branchCode);

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