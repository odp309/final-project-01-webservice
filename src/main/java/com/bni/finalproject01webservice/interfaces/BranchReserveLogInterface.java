package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.branch_reserve_log.request.BranchReserveLogRequestDTO;
import com.bni.finalproject01webservice.dto.branch_reserve_log.response.BranchReserveLogResponseDTO;

public interface BranchReserveLogInterface {

    BranchReserveLogResponseDTO addBranchReserveLog(BranchReserveLogRequestDTO request);
}
