package com.app.inven.schedular;

import com.app.inven.dao.StockRepository;
import com.app.inven.pojo.Stock;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AutoReorderScheduler {

    private final Logger logger = LoggerFactory.getLogger(AutoReorderScheduler.class);
    private final StockRepository stockRepository;

    @Scheduled(cron = "0 0 * * * *") // Runs every hour
    public void checkAndLogLowStock() {
        List<Stock> lowStocks = stockRepository.findAll()
                .stream()
                .filter(Stock::needsReorder)
                .collect(Collectors.toList());

        lowStocks.forEach(stock -> logger.warn("Low stock alert: Product ID {} has only {} units left.",
                stock.getProduct().getProductId(), stock.getQuantity()));
    }
}