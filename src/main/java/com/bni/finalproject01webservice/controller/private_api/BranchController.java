package com.bni.finalproject01webservice.controller.private_api;

import com.bni.finalproject01webservice.dto.branch.request.BranchRequestDTO;
import com.bni.finalproject01webservice.dto.branch.response.BranchResponseDTO;
import com.bni.finalproject01webservice.interfaces.BranchInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/private/branch")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API", description = "Private API secured with JWT token")
public class BranchController {

    @Autowired
    private BranchInterface branchService;

    @PostMapping("/get")
    public List<BranchResponseDTO> getBankAccount(@RequestBody BranchRequestDTO request) {
        return branchService.getAllBranchOrderByDistance(request);
    }
}