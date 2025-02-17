package com.bni.finalproject01webservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "branch")
public class Branch {

    @Id
    private String code;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String province;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    /////////////////////// BIDIRECTIONAL RELATION BLOCK //////////////////////

    @OneToMany(mappedBy = "branch")
    private List<Employee> employees;

    @OneToMany(mappedBy = "branch")
    private List<Withdrawal> withdrawals;

    @OneToMany(mappedBy = "branch")
    private List<BranchReserve> branchReserves;
}