package com.app.inven.service;

import java.util.List;
import java.util.Optional;

import com.app.inven.pojo.Stock;



import java.util.List;
import com.app.inven.pojo.Stock;

public interface StockService {
    Stock addStock(Stock stock);
    List<Stock> getAllStocks();
    Stock getStockById(Long id);
    Stock updateStock(Long id, Stock stockDetails);
    boolean deleteStock(Long id);
    Stock save(Stock stock);
    List<Stock> getStockByProductId(Long productId);
}
