package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.BankAccountResponseDTO;

public interface BankAccountInterface {
    BankAccountResponseDTO getSaldo(String noRekening);
}
