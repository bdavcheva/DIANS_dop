package com.example.stock_demo.kafka;

import com.example.stock_demo.model.Stock;
import com.example.stock_demo.model.StockResponseDTO;
import com.example.stock_demo.repository.StockRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;


import java.util.Map;

@Service
public class KafkaConsumer {
    final StockRepository stockRepository;
    final SimpMessagingTemplate messagingTemplate;

    private final ObjectMapper objectMapper;

    public KafkaConsumer(StockRepository stockRepository, SimpMessagingTemplate messagingTemplate, ObjectMapper objectMapper) {
        this.stockRepository = stockRepository;
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${kafka.topic}", groupId = "stock-group")
    public void consume(String message) throws JsonProcessingException {
        StockResponseDTO stockResponse = objectMapper.readValue(message, StockResponseDTO.class);
        String symbol = stockResponse.getMeta().getSymbol();
        Stock existing = stockRepository.findBySymbol(symbol);

        if (existing != null) {
            existing.setData(message);
            stockRepository.save(existing);
        } else {
            Stock stock = new Stock();
            stock.setSymbol(symbol);
            stock.setData(message);
            stockRepository.save(stock);
        }
        
        messagingTemplate.convertAndSend("/topic/stocks", Map.of("symbol", symbol));
    }
}
