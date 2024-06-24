package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;
import com.bni.finalproject01webservice.interfaces.DateTimeInterface;
import com.bni.finalproject01webservice.interfaces.OperationTypeInterface;
import com.bni.finalproject01webservice.model.OperationType;
import com.bni.finalproject01webservice.repository.OperationTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class OperationTypeService implements OperationTypeInterface {

    private final OperationTypeRepository operationTypeRepository;

    private final DateTimeInterface dateTimeService;

    @Override
    public InitResponseDTO initOperationType() {
        OperationType currDebit = operationTypeRepository.findByName("D");
        OperationType currCredit = operationTypeRepository.findByName("K");

        OperationType debit = new OperationType();
        OperationType credit = new OperationType();

        if (currDebit == null) {
            debit.setName("D");
            debit.setDescription("Pengurangan saldo nasabah");
            debit.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            operationTypeRepository.save(debit);
        }

        if (currCredit == null) {
            credit.setName("K");
            credit.setDescription("Penambahan saldo nasabah");
            credit.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            operationTypeRepository.save(credit);
        }

        InitResponseDTO response = new InitResponseDTO();
        response.setMessage("Operation type init done!");

        return response;
    }
}
