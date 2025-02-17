package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.transfer_valas.request.DetailTransferValasRequestDTO;
import com.bni.finalproject01webservice.dto.transfer_valas.request.TransferValasRequestDTO;
import com.bni.finalproject01webservice.dto.transfer_valas.response.DetailTransferValasResponseDTO;
import com.bni.finalproject01webservice.dto.transfer_valas.response.TransferValasResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface TransferValasInterface {

    DetailTransferValasResponseDTO detailTransferValas(DetailTransferValasRequestDTO request);

    TransferValasResponseDTO transferValas(TransferValasRequestDTO request);

    //////////////////////////////// VERSION 2.0 BLOCK ////////////////////////////////

    DetailTransferValasResponseDTO detailTransferValas(DetailTransferValasRequestDTO request, HttpServletRequest headerRequest);

    TransferValasResponseDTO transferValas(TransferValasRequestDTO request, HttpServletRequest headerRequest);
}
