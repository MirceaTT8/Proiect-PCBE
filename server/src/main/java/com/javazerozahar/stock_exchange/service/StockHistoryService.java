package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.converters.StockHistoryConverter;
import com.javazerozahar.stock_exchange.model.dto.StockHistoryDTO;
import com.javazerozahar.stock_exchange.repository.StockHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StockHistoryService {

    private final StockHistoryRepository stockHistoryRepository;
    private final StockHistoryConverter stockHistoryConverter;

    public List<StockHistoryDTO> getStockHistoryOfStock(Long stockId, Integer days) {
        List<StockHistoryDTO> historyData;

        if (days != null && days > 0) {
            Instant fromDate = Instant.now().minus(days, ChronoUnit.DAYS);
            historyData = stockHistoryRepository
                    .findByStockIdAndTimestampAfter(stockId, fromDate.toEpochMilli())
                    .stream()
                    .map(stockHistoryConverter::toStockHistoryDTO).toList();
        } else {
            historyData = stockHistoryRepository
                    .findByStockId(stockId)
                    .stream()
                    .map(stockHistoryConverter::toStockHistoryDTO).toList();
        }

        historyData = sampleData(historyData, 50);

        return historyData;
    }

    private List<StockHistoryDTO> sampleData(List<StockHistoryDTO> data, int maxPoints) {
        int dataSize = data.size();
        if (dataSize <= maxPoints) {
            return data;
        }

        List<StockHistoryDTO> sampledData = new ArrayList<>();
        double samplingInterval = (double) dataSize / maxPoints;
        double nextSamplePoint = 0;

        for (int i = 0; i < dataSize; i++) {
            if (i >= Math.ceil(nextSamplePoint)) {
                sampledData.add(data.get(i));
                nextSamplePoint += samplingInterval;
            }
        }

        return sampledData;
    }
}
