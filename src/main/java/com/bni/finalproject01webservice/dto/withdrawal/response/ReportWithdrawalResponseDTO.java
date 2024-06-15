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
public class ReportWithdrawalResponseDTO {

    private List<MonthDetailResponseDTO> month;
}
