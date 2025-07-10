package com.app.inven.dao;



import com.app.inven.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.inven.pojo.Stock;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByProductProductId(Long productId);
    Optional<Stock> findByProduct(Product product);
}
