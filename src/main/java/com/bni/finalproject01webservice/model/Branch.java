package com.bni.finalproject01webservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "branch")
public class Branch {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "code_area", unique = true, nullable = false)
    private int codeArea;

    @Column(nullable = false)
    private String address;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    /////////////////////// BIDIRECTIONAL RELATION BLOCK //////////////////////

    @OneToMany(mappedBy = "branch")
    private List<Employee> employees;

    @OneToMany(mappedBy = "branch")
    private List<OrderValas> orders;

    @OneToMany(mappedBy = "branch")
    private List<BranchReserve> reserves;
}
