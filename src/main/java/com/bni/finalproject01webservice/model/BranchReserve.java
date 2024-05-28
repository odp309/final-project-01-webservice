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
@Table(name = "branch_reserve")
public class BranchReserve {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(name = "temp_balance", nullable = false)
    private BigDecimal tempBalance;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    //////////////////////// FOREIGN KEY RELATION BLOCK ///////////////////////

    @ManyToOne
    @JoinColumn(name = "id_branch", nullable = false)
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "id_currency", nullable = false)
    private Currency currency;
}
