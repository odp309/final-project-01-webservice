package com.bni.finalproject01webservice.dto;

import java.util.Map;

public class ExchangeRateDTO {
    private String base;
    private Map<String, Double> rates;
    private Map<String, Double> buyRates;
    private Map<String, Double> sellRates;

    public String getBase() {
        return base;
    }

    public Map<String, Double> getBuyRates() {
        return buyRates;
    }

    public void setBuyRates(Map<String, Double> buyRates) {
        this.buyRates = buyRates;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    public Map<String, Double> getSellRates() {
        return sellRates;
    }

    public void setSellRates(Map<String, Double> sellRates) {
        this.sellRates = sellRates;
    }
}
