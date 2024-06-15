package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.history.request.HistoryDataRequestDTO;
import com.bni.finalproject01webservice.dto.history.request.HistoryDetailRequestDTO;
import com.bni.finalproject01webservice.dto.history.response.HistoryDataResponseDTO;
import com.bni.finalproject01webservice.dto.history.response.HistoryDetailResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface HistoryInterface {

    List<HistoryDataResponseDTO> getHistoryData(HistoryDataRequestDTO request);

    HistoryDetailResponseDTO getHistoryDetail(HistoryDetailRequestDTO request);

    //////////////////////////////// VERSION 2.0 BLOCK ////////////////////////////////

    List<HistoryDataResponseDTO> getHistoryData(HistoryDataRequestDTO request, HttpServletRequest headerRequest);

    HistoryDetailResponseDTO getHistoryDetail(HistoryDetailRequestDTO request, HttpServletRequest headerRequest);
}
