package com.bni.finalproject01webservice.dto.withdrawal.request;

import com.bni.finalproject01webservice.model.Branch;
import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.model.Wallet;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class WithdrawalRequestDTO {

    private User user;
    private Wallet wallet;
    private Branch branch;
    private String status;
    private BigDecimal amount;
    private Date reservationDate;
}