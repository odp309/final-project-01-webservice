package com.bni.finalproject01webservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "trx_type")
public class TrxType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    /////////////////////// BIDIRECTIONAL RELATION BLOCK //////////////////////

    @OneToMany(mappedBy = "trxType")
    private List<FinancialTrx> financialTrxs;

    @OneToMany(mappedBy = "trxType")
    private List<WithdrawalTrx> withdrawalTrxs;
}
