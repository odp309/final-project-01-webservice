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
@Table(name = "order_valas")
public class OrderValas {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "order_number", nullable = false)
    private String orderNumber;

    @Column(nullable = false)
    private String status;

    @Column(name = "in_progress", nullable = false)
    private boolean inProgress;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    //////////////////////// FOREIGN KEY RELATION BLOCK ///////////////////////

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_wallet", nullable = false)
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "id_branch", nullable = false)
    private Branch branch;
}
