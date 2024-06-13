package com.bni.finalproject01webservice.service;

import com.bni.finalproject01webservice.dto.branch_reserve_log.request.BranchReserveLogRequestDTO;
import com.bni.finalproject01webservice.dto.branch_reserve_log.response.BranchReserveLogResponseDTO;
import com.bni.finalproject01webservice.interfaces.BranchReserveLogInterface;
import com.bni.finalproject01webservice.model.BranchReserveLog;
import com.bni.finalproject01webservice.model.OperationType;
import com.bni.finalproject01webservice.repository.BranchReserveLogRepository;
import com.bni.finalproject01webservice.repository.OperationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class BranchReserveLogService implements BranchReserveLogInterface {

    private final BranchReserveLogRepository branchReserveLogRepository;
    private final OperationTypeRepository operationTypeRepository;

    @Override
    public BranchReserveLogResponseDTO addBranchReserveLog(BranchReserveLogRequestDTO request) {

        OperationType operationType = operationTypeRepository.findByName(request.getOperationTypeName());

        BranchReserveLog branchReserveLog = new BranchReserveLog();
        branchReserveLog.setAmount(request.getAmount());
        branchReserveLog.setCurrentBalance(request.getCurrentBalance());
        branchReserveLog.setUpdatedBalance(request.getUpdatedBalance());
        branchReserveLog.setUpdatedBy(request.getUpdatedBy());
        branchReserveLog.setOperationType(operationType);
        branchReserveLog.setBranchReserve(request.getBranchReserve());
        branchReserveLog.setCreatedAt(new Date());
        branchReserveLogRepository.save(branchReserveLog);

        BranchReserveLogResponseDTO response = new BranchReserveLogResponseDTO();
        response.setBranchReserveLogId(branchReserveLog.getId());

        return response;
    }
}
