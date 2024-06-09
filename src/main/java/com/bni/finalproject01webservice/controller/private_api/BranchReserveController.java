package com.bni.finalproject01webservice.controller.private_api;

import com.bni.finalproject01webservice.dto.branch_reserve.request.AddBranchReserveRequestDTO;
import com.bni.finalproject01webservice.dto.branch_reserve.response.BranchReserveResponseDTO;
import com.bni.finalproject01webservice.interfaces.BranchReserveInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}