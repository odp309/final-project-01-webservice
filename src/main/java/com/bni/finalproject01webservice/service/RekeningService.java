package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.RekeningResponseDTO;
import com.bni.finalproject01webservice.model.Rekening;
import com.bni.finalproject01webservice.repository.RekeningRepository;
import com.bni.finalproject01webservice.interfaces.RekeningInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RekeningService implements RekeningInterface {

    @Autowired
    private RekeningRepository rekeningRepository;

    @Override
    public RekeningResponseDTO getSaldo(String noRekening) {
        Rekening rekening = rekeningRepository.findByNoRekening(noRekening);
        if (rekening == null) {
            throw new RuntimeException("Rekening not found");
        }
        RekeningResponseDTO response = new RekeningResponseDTO();
        response.setNoRekening(rekening.getNoRekening());
        response.setIdNasabah(rekening.getIdNasabah());
        response.setSaldo(rekening.getSaldo());
        return response;
    }

}
