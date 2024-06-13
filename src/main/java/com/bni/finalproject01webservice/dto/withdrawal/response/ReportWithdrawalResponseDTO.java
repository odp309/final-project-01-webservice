package com.bni.finalproject01webservice.dto.withdrawal.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ReportWithdrawalResponseDTO {

    private Map<String, List<RecapWithdrawalResponseDTO>> monthlyWithdrawalDetail = new HashMap<>();

    @JsonAnyGetter
    public Map<String, List<RecapWithdrawalResponseDTO>> getMonthlyWithdrawalDetail() {
        return monthlyWithdrawalDetail;
    }

    @JsonAnySetter
    public void setMonthlyWithdrawalDetail(String month, List<RecapWithdrawalResponseDTO> sales) {
        this.monthlyWithdrawalDetail.put(month, sales);
    }
}
