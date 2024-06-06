package com.bni.finalproject01webservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "trx_history")
public class TrxHistory {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    //////////////////////// FOREIGN KEY RELATION BLOCK ///////////////////////

    @OneToOne
    @JoinColumn(name = "financial_trx_id", referencedColumnName = "id")
    private FinancialTrx financialTrx;

    @OneToOne
    @JoinColumn(name = "withdrawal_trx_id", referencedColumnName = "id")
    private WithdrawalTrx withdrawalTrx;
}
