package com.bni.finalproject01webservice.interfaces;

import com.bni.finalproject01webservice.dto.history.request.HistoryDataRequestDTO;
import com.bni.finalproject01webservice.dto.history.response.HistoryDataResponseDTO;

import java.util.List;

public interface HistoryInterface {
    List<HistoryDataResponseDTO> getHistoryData(HistoryDataRequestDTO request);
}
