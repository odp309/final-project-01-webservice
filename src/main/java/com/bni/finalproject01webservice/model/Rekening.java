package com.bni.finalproject01webservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rekening")
public class Rekening {

    @Id
    @Column(name= "no_rekening", nullable = false, unique = true)
    private String noRekening;

    @Column(name = "id_nasabah", nullable = false, unique = true)
    private Long idNasabah;

    @Column(name = "saldo")
    private Integer saldo;
}
