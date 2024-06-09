package com.bni.finalproject01webservice.controller.public_api;

import com.bni.finalproject01webservice.dto.branch_reserve.request.InitBranchReserveRequestDTO;
import com.bni.finalproject01webservice.dto.init.response.InitResponseDTO;
import com.bni.finalproject01webservice.interfaces.BranchReserveInterface;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public/init")
@Tag(name = "Public API", description = "Public API ")
public class BranchReserveController {

    @Autowired
    private BranchReserveInterface branchReserveService;


    @PostMapping("/branch-reserve")
  // @PreAuthorize("hasRole('ADMIN_MGR')")
    public ResponseEntity<InitResponseDTO> initBranch(@RequestBody InitBranchReserveRequestDTO request) {
        InitResponseDTO result = branchReserveService.initBranchReserve(request);
        return ResponseEntity.ok(result);
    }
}


