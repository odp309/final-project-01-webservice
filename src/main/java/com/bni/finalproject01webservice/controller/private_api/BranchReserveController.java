package com.bni.finalproject01webservice.controller.private_api;

import com.bni.finalproject01webservice.dto.branch_reserve.request.AddBranchReserveRequestDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.request.AddStockRequestDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.request.GetBranchReserveRequestDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.response.AddStockResponseDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.response.BranchReserveResponseDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.response.GetBranchReserveResponseDTO;
import com.bni.finalproject01webservice.interfaces.BranchReserveInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/private/branch-reserve")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API", description = "Private API secured with JWT token")
public class BranchReserveController {

    @Autowired
    private BranchReserveInterface branchReserveService;

    @PostMapping("/add")
    public BranchReserveResponseDTO addBranchReserve(@RequestBody AddBranchReserveRequestDTO request) {
        return branchReserveService.addBranchReserve(request);
    }

    @PostMapping("/add-stock")
    @PreAuthorize("hasRole('ADMIN')")
    public AddStockResponseDTO addStock(@RequestBody AddStockRequestDTO request) {
        return branchReserveService.addStockBranchReserve(request);
    }

    @PostMapping("/get")
    public List<GetBranchReserveResponseDTO> getBranchReserve(@RequestBody GetBranchReserveRequestDTO request) {
        return branchReserveService.getBranchReserveList(request);
    }

}