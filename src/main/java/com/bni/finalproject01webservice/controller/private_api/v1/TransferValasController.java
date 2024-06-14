package com.bni.finalproject01webservice.controller.private_api.v1;

import com.bni.finalproject01webservice.dto.transfer_valas.request.DetailTransferValasRequestDTO;
import com.bni.finalproject01webservice.dto.transfer_valas.request.TransferValasRequestDTO;
import com.bni.finalproject01webservice.dto.transfer_valas.response.DetailTransferValasResponseDTO;
import com.bni.finalproject01webservice.dto.transfer_valas.response.TransferValasResponseDTO;
import com.bni.finalproject01webservice.interfaces.TransferValasInterface;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/private/transfer-valas")
@SecurityRequirement(name = "access-token")
@Tag(name = "Private API", description = "Private API secured with JWT token")
public class TransferValasController {

    @Autowired
    private TransferValasInterface transferValasService;

    @PostMapping("/detail")
    public DetailTransferValasResponseDTO detailTransferValas(@RequestBody DetailTransferValasRequestDTO request) {
        return transferValasService.detailTransferValas(request);
    }

    @PostMapping("/transfer")
    public TransferValasResponseDTO transferValas(@RequestBody TransferValasRequestDTO request) {
        return transferValasService.transferValas(request);
    }
}
