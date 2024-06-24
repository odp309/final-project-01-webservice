package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.financial_trx.request.FinancialTrxRequestDTO;
import com.bni.finalproject01webservice.dto.financial_trx.response.FinancialTrxResponseDTO;
import com.bni.finalproject01webservice.interfaces.DateTimeInterface;
import com.bni.finalproject01webservice.interfaces.FinancialTrxInterface;
import com.bni.finalproject01webservice.model.*;
import com.bni.finalproject01webservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class FinancialTrxService implements FinancialTrxInterface {

    private final FinancialTrxRepository financialTrxRepository;
    private final BankAccountRepository bankAccountRepository;
    private final TrxTypeRepository trxTypeRepository;
    private final OperationTypeRepository operationTypeRepository;

    private final DateTimeInterface dateTimeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FinancialTrxResponseDTO addFinancialTrx(FinancialTrxRequestDTO request) {
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(request.getAccountNumber());
        TrxType trxType = trxTypeRepository.findByName(request.getTrxTypeName());
        OperationType operationType = operationTypeRepository.findByName(request.getOperationTypeName());

        FinancialTrx financialTrx = new FinancialTrx();
        financialTrx.setAmount(request.getAmount());
        if (request.getTrxTypeName().equals("Transfer")) {
            financialTrx.setDetail(
                    request.getTrxTypeName() + "/" +
                            request.getAmount().stripTrailingZeros().toPlainString() + " " + request.getWallet().getCurrency().getCode() + "/" +
                            bankAccount.getUser().getFirstName() + " " + bankAccount.getUser().getLastName() + "/" +
                            bankAccount.getAccountNumber());
        } else {
            financialTrx.setDetail(
                    request.getTrxTypeName() + "/" +
                            request.getAmount().stripTrailingZeros().toPlainString() + " " + request.getWallet().getCurrency().getCode() + "/" +
                            request.getRate() + "/" +
                            "PT Bank Negara Indonesia (Persero), Tbk");
        }
        financialTrx.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
        financialTrx.setUser(request.getUser());
        financialTrx.setWallet(request.getWallet());
        financialTrx.setTrxType(trxType);
        financialTrx.setOperationType(operationType);
        financialTrxRepository.save(financialTrx);

        FinancialTrxResponseDTO response = new FinancialTrxResponseDTO();
        response.setFinancialTrxId(financialTrx.getId());

        return response;
    }
}
