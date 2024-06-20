package com.bni.finalproject01webservice.dto.withdrawal_detail.request;

import com.bni.finalproject01webservice.model.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawalDetailRequestDTO {

    private Withdrawal withdrawal;
    private String trxTypeName;
    private String operationTypeName;
    private BigDecimal rate;
}
