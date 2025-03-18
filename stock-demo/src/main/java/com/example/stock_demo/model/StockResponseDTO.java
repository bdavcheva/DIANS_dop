package com.example.stock_demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class StockResponseDTO {

    private StockMetaDTO meta;
    private List<StockValueDTO> values;
    private String status;

    public StockResponseDTO() {
    }

    public StockResponseDTO(StockMetaDTO meta, List<StockValueDTO> values, String status) {
        this.meta = meta;
        this.values = values;
        this.status = status;
    }

    @JsonProperty("meta")
    public StockMetaDTO getMeta() {
        return meta;
    }

    public void setMeta(StockMetaDTO meta) {
        this.meta = meta;
    }

    @JsonProperty("values")
    public List<StockValueDTO> getValues() {
        return values;
    }

    public void setValues(List<StockValueDTO> values) {
        this.values = values;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}