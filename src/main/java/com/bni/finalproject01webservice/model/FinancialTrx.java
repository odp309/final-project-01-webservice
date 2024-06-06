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
@Table(name = "financial_trx")
public class FinancialTrx {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String detail;

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
    @JoinColumn(name = "trx_type_id", nullable = false)
    private TrxType trxType;

    @ManyToOne
    @JoinColumn(name = "operation_type_id", nullable = false)
    private OperationType operationType;

    /////////////////////// BIDIRECTIONAL RELATION BLOCK //////////////////////

    @OneToOne(mappedBy = "financialTrx", cascade = CascadeType.ALL, orphanRemoval = true)
    private TrxHistory trxHistory;
}
