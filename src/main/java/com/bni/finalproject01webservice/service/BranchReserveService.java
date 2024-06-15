package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.branch_reserve.request.AddBranchReserveRequestDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.request.AddStockRequestDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.request.GetBranchReserveRequestDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.response.AddStockResponseDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.response.BranchReserveResponseDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.response.GetBranchReserveResponseDTO;
import com.bni.finalproject01webservice.dto.branch_reserve_log.request.BranchReserveLogRequestDTO;
import com.bni.finalproject01webservice.dto.branch_reserve_log.response.BranchReserveLogResponseDTO;
import com.bni.finalproject01webservice.interfaces.BranchReserveInterface;
import com.bni.finalproject01webservice.interfaces.BranchReserveLogInterface;
import com.bni.finalproject01webservice.interfaces.JWTInterface;
import com.bni.finalproject01webservice.interfaces.ResourceRequestCheckerInterface;
import com.bni.finalproject01webservice.model.Branch;
import com.bni.finalproject01webservice.model.BranchReserve;
import com.bni.finalproject01webservice.model.Currency;
import com.bni.finalproject01webservice.repository.BranchRepository;
import com.bni.finalproject01webservice.repository.BranchReserveRepository;
import com.bni.finalproject01webservice.repository.CurrencyRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchReserveService implements BranchReserveInterface {

    private final BranchReserveRepository branchReserveRepository;
    private final CurrencyRepository currencyRepository;
    private final BranchRepository branchRepository;

    private final BranchReserveLogInterface branchReserveLogService;
    private final JWTInterface jwtService;
    private final ResourceRequestCheckerInterface resourceRequestCheckerService;

    @Override
    public BranchReserveResponseDTO addBranchReserve(AddBranchReserveRequestDTO request) {

        BranchReserve currentBranchReserve = branchReserveRepository.findByBranchCodeAndCurrencyCode(request.getBranchCode(), request.getCurrencyCode());

        if (currentBranchReserve != null) {
            throw new RuntimeException("Branch Reserve already exist!");
        }

        Branch branch = branchRepository.findById(request.getBranchCode()).orElseThrow(() -> new RuntimeException("Branch not found!"));
        Currency currency = currencyRepository.findByCode(request.getCurrencyCode());

        BranchReserve reserve = new BranchReserve();
        reserve.setBranch(branch);
        reserve.setBalance(BigDecimal.ZERO);
        reserve.setCurrency(currency);
        reserve.setCreatedAt(new Date());
        branchReserveRepository.save(reserve);

        BranchReserveResponseDTO response = new BranchReserveResponseDTO();
        response.setBranchCode(reserve.getBranch().getCode());
        response.setBranchName(reserve.getBranch().getName());
        response.setCurrencyCode(reserve.getCurrency().getCode());
        response.setBalance(reserve.getBalance());

        return response;
    }

    @Override
    public AddStockResponseDTO addStockBranchReserve(AddStockRequestDTO request, HttpServletRequest headerRequest) {

        String token = headerRequest.getHeader("Authorization").substring(7);

        BranchReserve currentBranchReserve = branchReserveRepository.findByBranchCodeAndCurrencyCode(String.valueOf(jwtService.extractAllClaims(token).get("branchCode")), request.getCurrencyCode());

        if (currentBranchReserve == null) {
            throw new RuntimeException("Branch Reserve not found!");
        }

        BigDecimal updatedBalance = currentBranchReserve.getBalance().add(request.getAmount());

        BranchReserveLogRequestDTO logRequest = new BranchReserveLogRequestDTO();
        logRequest.setAmount(request.getAmount());
        logRequest.setCurrentBalance(currentBranchReserve.getBalance());
        logRequest.setUpdatedBalance(updatedBalance);
        logRequest.setUpdatedBy(String.valueOf(jwtService.extractAllClaims(token).get("id")));
        logRequest.setOperationTypeName("K");
        logRequest.setBranchReserve(currentBranchReserve);
        BranchReserveLogResponseDTO logResponse = branchReserveLogService.addBranchReserveLog(logRequest);

        currentBranchReserve.setBalance(updatedBalance);
        currentBranchReserve.setUpdatedAt(new Date());
        branchReserveRepository.save(currentBranchReserve);

        AddStockResponseDTO response = new AddStockResponseDTO();
        response.setMessage("Success");
        response.setBranchReserveLogId(logResponse.getBranchReserveLogId());

        return response;
    }

    @Override
    public List<GetBranchReserveResponseDTO> getBranchReserveList(GetBranchReserveRequestDTO request) {
        List<BranchReserve> branchReserveList = branchReserveRepository.findByBranchCode(request.getBranchCode());

        return branchReserveList.stream()
                .map(branchReserve -> {
                    GetBranchReserveResponseDTO response = new GetBranchReserveResponseDTO();
                    response.setCurrencyCode(branchReserve.getCurrency().getCode());
                    response.setCurrencyName(branchReserve.getCurrency().getName());
                    response.setBalance(branchReserve.getBalance());
                    response.setUpdatedAt(branchReserve.getUpdatedAt());
                    return response;
                })
                .collect(Collectors.toList());
    }

    //////////////////////////////// VERSION 2.0 BLOCK ////////////////////////////////

    @Override
    public BranchReserveResponseDTO addBranchReserve(AddBranchReserveRequestDTO request, HttpServletRequest headerRequest) {

        String branchCode = resourceRequestCheckerService.extractBranchCodeFromToken(headerRequest);

        BranchReserve currentBranchReserve = branchReserveRepository.findByBranchCodeAndCurrencyCode(branchCode, request.getCurrencyCode());

        if (currentBranchReserve != null) {
            throw new RuntimeException("Branch Reserve already exist!");
        }

        Branch branch = branchRepository.findById(branchCode).orElseThrow(() -> new RuntimeException("Branch not found!"));
        Currency currency = currencyRepository.findByCode(request.getCurrencyCode());

        BranchReserve reserve = new BranchReserve();
        reserve.setBranch(branch);
        reserve.setBalance(BigDecimal.ZERO);
        reserve.setCurrency(currency);
        reserve.setCreatedAt(new Date());
        branchReserveRepository.save(reserve);

        BranchReserveResponseDTO response = new BranchReserveResponseDTO();
        response.setBranchCode(reserve.getBranch().getCode());
        response.setBranchName(reserve.getBranch().getName());
        response.setCurrencyCode(reserve.getCurrency().getCode());
        response.setBalance(reserve.getBalance());

        return response;
    }

    @Override
    public List<GetBranchReserveResponseDTO> getBranchReserveList(GetBranchReserveRequestDTO request, HttpServletRequest headerRequest) {

        String branchCode = resourceRequestCheckerService.extractBranchCodeFromToken(headerRequest);

        List<BranchReserve> branchReserveList = branchReserveRepository.findByBranchCode(branchCode);

        return branchReserveList.stream()
                .map(branchReserve -> {
                    GetBranchReserveResponseDTO response = new GetBranchReserveResponseDTO();
                    response.setCurrencyCode(branchReserve.getCurrency().getCode());
                    response.setCurrencyName(branchReserve.getCurrency().getName());
                    response.setBalance(branchReserve.getBalance());
                    response.setUpdatedAt(branchReserve.getUpdatedAt());
                    return response;
                })
                .collect(Collectors.toList());
    }
}