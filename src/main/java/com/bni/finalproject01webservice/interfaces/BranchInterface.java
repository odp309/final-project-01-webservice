package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.branch.request.BranchRequestDTO;
import com.bni.finalproject01webservice.dto.branch.response.BranchResponseDTO;
import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;

import java.util.List;

public interface BranchInterface {

    InitResponseDTO initBranch();

    List<BranchResponseDTO> getAllBranchOrderByDistance(BranchRequestDTO request);
}
