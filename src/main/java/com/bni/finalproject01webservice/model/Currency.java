package com.bni.finalproject01webservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "currency")
public class Currency {

    @Id
    @Column(unique = true, nullable = false)
    private String code;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "minimum_deposit", nullable = false)
    private BigDecimal minimumDeposit;

    @Column(name = "minimum_buy", nullable = false)
    private BigDecimal minimumBuy;

    @Column(name = "minimum_sell", nullable = false)
    private BigDecimal minimumSell;

    @Column(name = "minimum_transfer", nullable = false)
    private BigDecimal minimumTransfer;

    @Column(name = "minimum_withdrawal", nullable = false)
    private BigDecimal minimumWithdrawal;

    @Column(nullable = false)
    private String flagIcon;

    @Column(nullable = false)
    private String landmarkIcon;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    /////////////////////// BIDIRECTIONAL RELATION BLOCK //////////////////////

    @OneToMany(mappedBy = "currency")
    private List<Wallet> wallets;

    @OneToMany(mappedBy = "currency", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExchangeRate> exchangeRates;
}
