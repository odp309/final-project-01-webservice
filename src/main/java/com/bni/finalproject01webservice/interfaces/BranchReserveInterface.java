package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.branch_reserve.request.AddBranchReserveRequestDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.request.AddStockRequestDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.response.AddStockResponseDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.response.BranchReserveResponseDTO;
import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;

public interface BranchReserveInterface {

    BranchReserveResponseDTO addBranchReserve(AddBranchReserveRequestDTO request);

    AddStockResponseDTO addStockBranchReserve(AddStockRequestDTO request);
}
