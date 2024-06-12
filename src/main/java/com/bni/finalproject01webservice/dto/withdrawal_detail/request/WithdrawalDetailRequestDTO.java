package com.bni.finalproject01webservice.dto.withdrawal_detail.request;

import com.bni.finalproject01webservice.model.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawalDetailRequestDTO {

    private User user;
    private Wallet wallet;
    private Withdrawal withdrawal;
    private String trxTypeName;
    private String operationTypeName;
}
