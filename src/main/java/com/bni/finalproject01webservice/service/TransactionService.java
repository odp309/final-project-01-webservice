package com.bni.finalproject01webservice.service;


import com.bni.finalproject01webservice.dto.response.InitResponseDTO;
import com.bni.finalproject01webservice.interfaces.TransactionInterface;
import com.bni.finalproject01webservice.model.TransactionType;
import com.bni.finalproject01webservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionInterface {
    private final TransactionRepository transactionRepository;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public InitResponseDTO initTransactionType() {



        TransactionType currBuy = transactionRepository.findByName("Beli");
        TransactionType currSell = transactionRepository.findByName("Jual");
        TransactionType currTransfer = transactionRepository.findByName("Transfer");
        TransactionType currWithdraw = transactionRepository.findByName("Tarik Valas");


        TransactionType buy = new TransactionType();
        TransactionType sell = new TransactionType();
        TransactionType transfer = new TransactionType();
        TransactionType withdraw = new TransactionType();

        String status = "";

        if(currBuy == null){
            buy.setCreatedAt(new Date());
            buy.setName("Beli");
            buy.setDescription("Pembelian Valas");
            transactionRepository.save(buy);
            status += "Transaction Type 'Beli' has been added!\n";
        }
        if(currSell == null){
            sell.setCreatedAt(new Date());
            sell.setName("Jual");
            sell.setDescription("Penjualan Valas");
            transactionRepository.save(sell);
            status += "Transaction Type 'Jual' has been added!\n";
        }
        if(currTransfer == null){
            transfer.setCreatedAt(new Date());
            transfer.setName("Transfer");
            transfer.setDescription("Transfer Valas");
            transactionRepository.save(transfer);
            status += "Transaction Type 'Transfer' has been added!\n";
        }
        if(currWithdraw == null){
            withdraw.setCreatedAt(new Date());
            withdraw.setName("Tarik  Valas");
            withdraw.setDescription("Penarikan Valas");
            transactionRepository.save(withdraw);
            status += "Transaction Type 'TarikValas' has been added!\n";
        }
            InitResponseDTO response = new InitResponseDTO();
            response.setMessage(
                    status.length() >= 157?
                            "All Transaction type has been initialized!" :
                            status.isEmpty()?
                                    "All Transaction type has been initialized!" :
                                    status
            );
            return response;

    }
}
