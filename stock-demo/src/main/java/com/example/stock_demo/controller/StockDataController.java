package com.example.stock_demo.controller;


import com.example.stock_demo.kafka.StockDataKafkaService;
import com.example.stock_demo.repository.StockRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StockDataController {

    private final StockDataKafkaService stockDataKafkaService;
    private final StockRepository stockRepository;

    public StockDataController(StockDataKafkaService stockDataKafkaService, StockRepository repository) {
        this.stockDataKafkaService = stockDataKafkaService;
        stockRepository = repository;
    }

    @GetMapping("/api/fetch-stock")
    public void fetchStockData(@RequestParam String symbol) {
        stockDataKafkaService.fetchAndSendData(symbol);
    }

    @GetMapping("/api/data")
    public Map<String, String> getDataForSymbol(@RequestParam String symbol) {
        return Map.of("data", stockRepository.findBySymbol(symbol).getData());
    }
}

