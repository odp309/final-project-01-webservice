package com.bni.finalproject01webservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String token;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    //////////////////////// FOREIGN KEY RELATION BLOCK ///////////////////////

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "id_employee", referencedColumnName = "id")
    private Employee employee;
}
