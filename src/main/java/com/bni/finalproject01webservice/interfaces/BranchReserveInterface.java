package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.branch_reserve.request.AddBranchReserveRequestDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.request.AddStockRequestDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.request.GetBranchReserveRequestDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.response.AddStockResponseDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.response.BranchReserveResponseDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.response.GetBranchReserveResponseDTO;
import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;
import com.bni.finalproject01webservice.dto.reservation_list.request.ReservationListRequestDTO;
import com.bni.finalproject01webservice.dto.reservation_list.response.ReservationListResponseDTO;

import java.util.List;

public interface BranchReserveInterface {

    BranchReserveResponseDTO addBranchReserve(AddBranchReserveRequestDTO request);

    AddStockResponseDTO addStockBranchReserve(AddStockRequestDTO request);

    List<GetBranchReserveResponseDTO> getBranchReserveList(GetBranchReserveRequestDTO request);


}
