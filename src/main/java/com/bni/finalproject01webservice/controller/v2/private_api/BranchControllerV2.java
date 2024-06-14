package com.bni.finalproject01webservice.controller.v2.private_api;

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
@RequestMapping("/api/v2/private/branch")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API V2", description = "Private API secured with JWT token")
public class BranchControllerV2 {

    @Autowired
    private BranchInterface branchService;

    @PostMapping("/get")
    public List<BranchResponseDTO> getBranchOrderByDistance(@RequestBody BranchRequestDTO request) {
        return branchService.getAllBranchOrderByDistance(request);
    }
}