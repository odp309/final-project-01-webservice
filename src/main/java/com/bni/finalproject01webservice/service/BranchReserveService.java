package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.configuration.exceptions.UserException;
import com.bni.finalproject01webservice.dto.branch_reserve.request.InitBranchReserveRequestDTO;
import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;
import com.bni.finalproject01webservice.interfaces.BranchReserveInterface;
import com.bni.finalproject01webservice.model.Branch;
import com.bni.finalproject01webservice.model.BranchReserve;
import com.bni.finalproject01webservice.model.Currency;
import com.bni.finalproject01webservice.model.User;
import com.bni.finalproject01webservice.repository.BranchRepository;
import com.bni.finalproject01webservice.repository.BranchReserveRepository;
import com.bni.finalproject01webservice.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class BranchReserveService implements BranchReserveInterface {

    @Autowired
    private BranchReserveRepository branchReserveRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private BranchRepository branchRepository;


    @Override
    public InitResponseDTO initBranchReserve(InitBranchReserveRequestDTO request) {
        InitResponseDTO response = new InitResponseDTO();

        BranchReserve current = branchReserveRepository.findByBranchNameAndCurrencyCode(request.getName(), request.getCurrencyCode());
        Branch branch = branchRepository.findByName(request.getName());
        Currency currency = currencyRepository.findByCode(request.getCurrencyCode());

        BranchReserve reserve = new BranchReserve();
        if (current == null) {

            reserve.setBranch(branch);
            reserve.setBalance(BigDecimal.ZERO);
            reserve.setCurrency(currency);
            reserve.setCreatedAt(new Date());
            reserve.setTempBalance(BigDecimal.ZERO);
            branchReserveRepository.save(reserve); // Corrected type parameter

            response.setMessage("New Branch Reserve has been successfully created");
        }
        else {
            response.setMessage("Branch Reserve already exist");
        }

        return response;
    }
}
