package com.example.stock_demo.kafka;


import com.example.stock_demo.repository.StockRepository;
import com.example.stock_demo.services.StockDataService;
import org.springframework.stereotype.Service;

@Service
public class StockDataKafkaService {
    final StockDataService stockDataService;
    final KafkaProducer kafkaProducer;

    public StockDataKafkaService(StockDataService stockDataService, KafkaProducer kafkaProducer) {
        this.stockDataService = stockDataService;
        this.kafkaProducer = kafkaProducer;
    }


    public void fetchAndSendData(String symbol) {
        String stockData = stockDataService.fetchStockData(symbol);
        kafkaProducer.sendMessage(stockData);
    }
}

