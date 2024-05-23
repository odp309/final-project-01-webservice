package com.bni.finalproject01webservice.controller;

import com.bni.finalproject01webservice.model.Rekening;
import com.bni.finalproject01webservice.interfaces.RekeningInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public/rekening")
public class RekeningController {

    @Autowired
    private RekeningInterface rekeningService;

    @GetMapping("/saldo/{noRekening}")
    public Rekening getSaldo(@PathVariable String noRekening) {
        return rekeningService.getSaldo(noRekening);
    }
}
