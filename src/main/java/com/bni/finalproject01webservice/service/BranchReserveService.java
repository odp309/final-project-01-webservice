package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.branch_reserve.request.AddBranchReserveRequestDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.response.BranchReserveResponseDTO;
import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;
import com.bni.finalproject01webservice.interfaces.BranchReserveInterface;
import com.bni.finalproject01webservice.model.Branch;
import com.bni.finalproject01webservice.model.BranchReserve;
import com.bni.finalproject01webservice.model.Currency;
import com.bni.finalproject01webservice.repository.BranchRepository;
import com.bni.finalproject01webservice.repository.BranchReserveRepository;
import com.bni.finalproject01webservice.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class BranchReserveService implements BranchReserveInterface {

    private final BranchReserveRepository branchReserveRepository;
    private final CurrencyRepository currencyRepository;
    private final BranchRepository branchRepository;

    @Override
    public BranchReserveResponseDTO addBranchReserve(AddBranchReserveRequestDTO request) {

        BranchReserve currentBranchReserve = branchReserveRepository.findByBranchNameAndCurrencyCode(request.getBranchName(), request.getCurrencyCode());

        if (currentBranchReserve != null) {
            throw new RuntimeException("Branch Reserve already exist!");
        }

        Branch branch = branchRepository.findByName(request.getBranchName());
        Currency currency = currencyRepository.findByCode(request.getCurrencyCode());

        BranchReserve reserve = new BranchReserve();
        reserve.setBranch(branch);
        reserve.setBalance(BigDecimal.ZERO);
        reserve.setCurrency(currency);
        reserve.setCreatedAt(new Date());
        reserve.setTempBalance(BigDecimal.ZERO);
        branchReserveRepository.save(reserve);

        BranchReserveResponseDTO response = new BranchReserveResponseDTO();
        response.setBranchName(reserve.getBranch().getName());
        response.setCurrencyCode(reserve.getCurrency().getCode());
        response.setBalance(reserve.getBalance());
        response.setTempBalance(reserve.getTempBalance());

        return response;
    }
}