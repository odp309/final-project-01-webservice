package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;
import com.bni.finalproject01webservice.interfaces.DateTimeInterface;
import com.bni.finalproject01webservice.interfaces.TrxTypeInterface;
import com.bni.finalproject01webservice.model.TrxType;
import com.bni.finalproject01webservice.repository.TrxTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class TrxTypeService implements TrxTypeInterface {

    private final TrxTypeRepository trxTypeRepository;

    private final DateTimeInterface dateTimeService;

    @Override
    public InitResponseDTO initTrxType() {
        TrxType currTransfer = trxTypeRepository.findByName("Transfer");
        TrxType currBuy = trxTypeRepository.findByName("Beli");
        TrxType currSell = trxTypeRepository.findByName("Jual");
        TrxType currWithdraw = trxTypeRepository.findByName("Tarik");
        TrxType currRefund = trxTypeRepository.findByName("Pengembalian Dana");

        TrxType transfer = new TrxType();
        TrxType buy = new TrxType();
        TrxType sell = new TrxType();
        TrxType withdraw = new TrxType();
        TrxType refund = new TrxType();

        if (currTransfer == null) {
            transfer.setName("Transfer");
            transfer.setDescription("Transfer valas antar BNI");
            transfer.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            trxTypeRepository.save(transfer);
        }

        if (currBuy == null) {
            buy.setName("Beli");
            buy.setDescription("Beli valas dari BNI");
            buy.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            trxTypeRepository.save(buy);
        }

        if (currSell == null) {
            sell.setName("Jual");
            sell.setDescription("Jual valas ke BNI");
            sell.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            trxTypeRepository.save(sell);
        }

        if (currWithdraw == null) {
            withdraw.setName("Tarik");
            withdraw.setDescription("Tarik valas BNI");
            withdraw.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            trxTypeRepository.save(withdraw);
        }

        if (currRefund == null) {
            refund.setName("Pengembalian Dana");
            refund.setDescription("Pengembalian valas ke nasabah");
            refund.setCreatedAt(dateTimeService.getCurrentDateTimeInJakarta());
            trxTypeRepository.save(refund);
        }

        InitResponseDTO response = new InitResponseDTO();
        response.setMessage("Trx type init done!");

        return response;
    }
}
