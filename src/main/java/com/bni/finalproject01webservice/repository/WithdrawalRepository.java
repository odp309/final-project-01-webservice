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

    Withdrawal findByReservationCode(String reservationCode);

    @Query(
            value=
            """
            SELECT
            w.reservation_code ,
            w.reservation_date ,
            w.created_at ,
            w.status ,
            w.amount ,
            w2.currency_code,
            w2.account_number,
            e.first_name as employeeFirstName, e.last_name as employeeLastName,
            u.first_name as customerFirstName , u.last_name as customerLastName
            FROM withdrawal w
            INNER JOIN employee e ON CAST(w.done_by AS UUID) = e.id
            inner join users u on w.user_id = u.id
            inner join wallet w2 on w.wallet_id = w2.id
            where w.branch_code = :code
            """,nativeQuery = true)
    List<Object[]> findBySelectedBranchCode (String code);

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

    @Query("""
            select
            	wd.id,
            	w.amount,
            	w.createdAt,
            	w.status,
            	w.wallet.currency.code,
            	wd.trxType.name,
            	wd.operationType.name
            from
            	WithdrawalDetail wd
            join
                wd.withdrawal w
            where
            	w.wallet.id = :walletId
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

    @Query(
            """
                    Select
                    w from
                    Withdrawal w
                    where w.status = 'Terjadwal'
                    and w.user.id = :userId
                    """
    )
    List<Withdrawal> findBySelectedId(UUID userId);
}