package com.bni.finalproject01webservice.interfaces;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

public interface ResourceRequestCheckerInterface {

    List<UUID> financialTrxChecker(HttpServletRequest headerRequest);

    List<UUID> walletChecker(HttpServletRequest headerRequest);

    List<String> bankAccountChecker(HttpServletRequest headerRequest);

    UUID extractIdFromToken(HttpServletRequest headerRequest);

    String extractBranchCodeFromToken(HttpServletRequest headerRequest);
}
