package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.branch_reserve.request.InitBranchReserveRequestDTO;
import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;

public interface BranchReserveInterface {

    InitResponseDTO initBranchReserve(InitBranchReserveRequestDTO request);
}
