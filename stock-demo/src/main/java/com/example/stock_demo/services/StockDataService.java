package com.example.stock_demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Service
public class StockDataService {

    private final RestTemplate restTemplate;

    @Value("${twelvedata.api.key}")
    private String apiKey;

    private static final String BASE_URL = "https://api.twelvedata.com/time_series";

    public StockDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchStockData(String symbol) {
        String url = String.format("%s?symbol=%s&interval=1day&apikey=%s", BASE_URL, symbol, apiKey);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}

