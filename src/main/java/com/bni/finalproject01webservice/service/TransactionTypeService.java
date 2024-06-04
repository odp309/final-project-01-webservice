package com.bni.finalproject01webservice.service;


import com.bni.finalproject01webservice.dto.response.InitResponseDTO;
import com.bni.finalproject01webservice.interfaces.TransactionTypeInterface;
import com.bni.finalproject01webservice.model.TransactionType;
import com.bni.finalproject01webservice.repository.TransactionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TransactionTypeService implements TransactionTypeInterface {
    private final TransactionTypeRepository transactionTypeRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InitResponseDTO initTransactionType() {

        TransactionType currBuy = transactionTypeRepository.findByName("Beli");
        TransactionType currSell = transactionTypeRepository.findByName("Jual");
        TransactionType currTransfer = transactionTypeRepository.findByName("Transfer");
        TransactionType currWithdraw = transactionTypeRepository.findByName("Tarik Valas");

        TransactionType buy = new TransactionType();
        TransactionType sell = new TransactionType();
        TransactionType transfer = new TransactionType();
        TransactionType withdraw = new TransactionType();

        String status = "";

        if (currBuy == null) {
            buy.setCreatedAt(new Date());
            buy.setName("Beli");
            buy.setDescription("Pembelian Valas");
            transactionTypeRepository.save(buy);
            status += "Transaction type 'Beli' has been added!";
        }

        if (currSell == null) {
            sell.setCreatedAt(new Date());
            sell.setName("Jual");
            sell.setDescription("Penjualan Valas");
            transactionTypeRepository.save(sell);
            status += "Transaction type 'Jual' has been added!";
        }

        if (currTransfer == null) {
            transfer.setCreatedAt(new Date());
            transfer.setName("Transfer");
            transfer.setDescription("Transfer Valas");
            transactionTypeRepository.save(transfer);
            status += "Transaction type 'Transfer' has been added!";
        }

        if (currWithdraw == null) {
            withdraw.setCreatedAt(new Date());
            withdraw.setName("Tarik Valas");
            withdraw.setDescription("Penarikan Valas");
            transactionTypeRepository.save(withdraw);
            status += "Transaction type 'Tarik Valas' has been added!";
        }

        InitResponseDTO response = new InitResponseDTO();
        response.setMessage(
                status.length() >= 167 ?
                        "All transaction type has been initialized!" :
                        status.isEmpty() ?
                                "All transaction type has been initialized!" :
                                status
        );

        return response;
    }
}
