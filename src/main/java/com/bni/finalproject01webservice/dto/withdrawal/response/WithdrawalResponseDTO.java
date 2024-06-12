package com.bni.finalproject01webservice.dto.withdrawal.response;

import com.bni.finalproject01webservice.model.Withdrawal;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class WithdrawalResponseDTO {

    private Withdrawal withdrawal;
}
