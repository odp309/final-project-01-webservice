package com.bni.finalproject01webservice.dto.withdrawal.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthDetailResponseDTO {

    private List<RecapWithdrawalResponseDTO> january;
    private List<RecapWithdrawalResponseDTO> february;
    private List<RecapWithdrawalResponseDTO> march;
    private List<RecapWithdrawalResponseDTO> april;
    private List<RecapWithdrawalResponseDTO> may;
    private List<RecapWithdrawalResponseDTO> june;
    private List<RecapWithdrawalResponseDTO> july;
    private List<RecapWithdrawalResponseDTO> august;
    private List<RecapWithdrawalResponseDTO> september;
    private List<RecapWithdrawalResponseDTO> october;
    private List<RecapWithdrawalResponseDTO> november;
    private List<RecapWithdrawalResponseDTO> december;
}
