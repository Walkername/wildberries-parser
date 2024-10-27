package ru.wildberries.analytics.dto;

import java.util.List;

public class PriceHistoryDTO {

    private List<PriceStateDTO> priceHistory;

    public List<PriceStateDTO> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<PriceStateDTO> priceHistory) {
        this.priceHistory = priceHistory;
    }
}
