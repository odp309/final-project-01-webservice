package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;
import com.bni.finalproject01webservice.interfaces.OperationTypeInterface;
import com.bni.finalproject01webservice.model.OperationType;
import com.bni.finalproject01webservice.repository.OperationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OperationTypeService implements OperationTypeInterface {

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Override
    public InitResponseDTO initOperationType() {
        OperationType currDebit = operationTypeRepository.findByName("D");
        OperationType currCredit = operationTypeRepository.findByName("C");

        OperationType debit = new OperationType();
        OperationType credit = new OperationType();

        if (currDebit == null) {
            debit.setName("D");
            debit.setDescription("Pengurangan saldo nasabah");
            debit.setCreatedAt(new Date());
            operationTypeRepository.save(debit);
        }

        if (currCredit == null) {
            credit.setName("C");
            credit.setDescription("Penambahan saldo nasabah");
            credit.setCreatedAt(new Date());
            operationTypeRepository.save(credit);
        }

        InitResponseDTO response = new InitResponseDTO();
        response.setMessage("Operation type init done!");

        return response;
    }
}
