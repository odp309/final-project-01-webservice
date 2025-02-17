package com.bni.finalproject01webservice.controller.private_api.v2;

import com.bni.finalproject01webservice.dto.transfer_valas.request.DetailTransferValasRequestDTO;
import com.bni.finalproject01webservice.dto.transfer_valas.request.TransferValasRequestDTO;
import com.bni.finalproject01webservice.dto.transfer_valas.response.DetailTransferValasResponseDTO;
import com.bni.finalproject01webservice.dto.transfer_valas.response.TransferValasResponseDTO;
import com.bni.finalproject01webservice.interfaces.TransferValasInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/private/transfer-valas")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API V2", description = "Private API secured with JWT token")
public class TransferValasControllerV2 {

    @Autowired
    private TransferValasInterface transferValasService;

    @PostMapping("/detail")
    public DetailTransferValasResponseDTO detailTransferValas(@RequestBody DetailTransferValasRequestDTO request, HttpServletRequest headerRequest) {
        return transferValasService.detailTransferValas(request, headerRequest);
    }

    @PostMapping("/transfer")
    public TransferValasResponseDTO transferValas(@RequestBody TransferValasRequestDTO request, HttpServletRequest headerRequest) {
        return transferValasService.transferValas(request, headerRequest);
    }
}
