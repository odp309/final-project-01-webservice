package com.bni.finalproject01webservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RekeningResponseDTO {
    private String noRekening;
    private Long idNasabah;
    private Integer saldo;
}
