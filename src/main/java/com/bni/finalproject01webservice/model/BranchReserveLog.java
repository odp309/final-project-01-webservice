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
@Table(name = "branch_reserve_log")
public class BranchReserveLog {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "current_balance", nullable = false)
    private BigDecimal currentBalance;

    @Column(name = "updated_balance", nullable = false)
    private BigDecimal updatedBalance;

    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    //////////////////////// FOREIGN KEY RELATION BLOCK ///////////////////////

    @ManyToOne
    @JoinColumn(name = "branch_reserve_id", nullable = false)
    private BranchReserve branchReserve;

    @ManyToOne
    @JoinColumn(name = "operation_type_id", nullable = false)
    private OperationType operationType;
}
