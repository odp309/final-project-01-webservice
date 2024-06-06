package com.bni.finalproject01webservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "withdrawal_trx")
public class WithdrawalTrx {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String detail;

    @Column(nullable = false)
    private String status;

    @Column(name = "reservation_number", unique = true, nullable = false)
    private String reservationNumber;

    @Column(name = "reservation_date", nullable = false)
    private Date reservationDate;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    //////////////////////// FOREIGN KEY RELATION BLOCK ///////////////////////

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "trx_type_id", nullable = false)
    private TrxType trxType;

    @ManyToOne
    @JoinColumn(name = "operation_type_id", nullable = false)
    private OperationType operationType;

    /////////////////////// BIDIRECTIONAL RELATION BLOCK //////////////////////

    @OneToOne(mappedBy = "withdrawalTrx", cascade = CascadeType.ALL, orphanRemoval = true)
    private TrxHistory trxHistory;
}
