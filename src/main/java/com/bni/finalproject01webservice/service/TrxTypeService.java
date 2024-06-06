package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;
import com.bni.finalproject01webservice.interfaces.TrxTypeInterface;
import com.bni.finalproject01webservice.model.TrxType;
import com.bni.finalproject01webservice.repository.TrxTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TrxTypeService implements TrxTypeInterface {

    @Autowired
    private TrxTypeRepository trxTypeRepository;

    @Override
    public InitResponseDTO initTrxType() {
        TrxType currTransfer = trxTypeRepository.findByName("Transfer");
        TrxType currBuy = trxTypeRepository.findByName("Beli");
        TrxType currSell = trxTypeRepository.findByName("Jual");
        TrxType currWithdraw = trxTypeRepository.findByName("Tarik");
        TrxType currRefund = trxTypeRepository.findByName("Refund");

        TrxType transfer = new TrxType();
        TrxType buy = new TrxType();
        TrxType sell = new TrxType();
        TrxType withdraw = new TrxType();
        TrxType refund = new TrxType();

        if (currTransfer == null) {
            transfer.setName("Transfer");
            transfer.setDescription("Transfer valas antar BNI");
            transfer.setCreatedAt(new Date());
            trxTypeRepository.save(transfer);
        }

        if (currBuy == null) {
            buy.setName("Beli");
            buy.setDescription("Beli valas dari BNI");
            buy.setCreatedAt(new Date());
            trxTypeRepository.save(buy);
        }

        if (currSell == null) {
            sell.setName("Jual");
            sell.setDescription("Jual valas ke BNI");
            sell.setCreatedAt(new Date());
            trxTypeRepository.save(sell);
        }

        if (currWithdraw == null) {
            withdraw.setName("Tarik");
            withdraw.setDescription("Tarik valas BNI");
            withdraw.setCreatedAt(new Date());
            trxTypeRepository.save(withdraw);
        }

        if (currRefund == null) {
            refund.setName("Refund");
            refund.setDescription("Refund valas ke nasabah");
            refund.setCreatedAt(new Date());
            trxTypeRepository.save(refund);
        }

        InitResponseDTO response = new InitResponseDTO();
        response.setMessage("Trx type init done!");

        return response;
    }
}
