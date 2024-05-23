package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.RekeningResponseDTO;
import com.bni.finalproject01webservice.model.Rekening;

public interface RekeningInterface {
    RekeningResponseDTO getSaldo(String noRekening);
}
