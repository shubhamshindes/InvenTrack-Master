package com.app.inven.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import com.app.inven.pojo.Stock;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByProductProductId(Long productId);
}
