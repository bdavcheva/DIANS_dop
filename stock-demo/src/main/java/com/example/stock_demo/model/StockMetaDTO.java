package com.example.stock_demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockMetaDTO {

    private String symbol;
    private String interval;
    private String currency;
    private String exchangeTimezone;
    private String exchange;
    private String micCode;
    private String type;

    public StockMetaDTO() {
    }

    public StockMetaDTO(String symbol, String interval, String currency, String exchangeTimezone, String exchange, String micCode, String type) {
        this.symbol = symbol;
        this.interval = interval;
        this.currency = currency;
        this.exchangeTimezone = exchangeTimezone;
        this.exchange = exchange;
        this.micCode = micCode;
        this.type = type;
    }

    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("interval")
    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("exchange_timezone")
    public String getExchangeTimezone() {
        return exchangeTimezone;
    }

    public void setExchangeTimezone(String exchangeTimezone) {
        this.exchangeTimezone = exchangeTimezone;
    }

    @JsonProperty("exchange")
    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    @JsonProperty("mic_code")
    public String getMicCode() {
        return micCode;
    }

    public void setMicCode(String micCode) {
        this.micCode = micCode;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

