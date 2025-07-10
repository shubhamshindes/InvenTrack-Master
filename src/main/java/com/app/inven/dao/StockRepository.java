package com.app.inven.dao;



import com.app.inven.pojo.Product;
import com.app.inven.pojo.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.inven.pojo.Stock;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByProductProductId(Long productId);

    List<Stock> findByProduct(Product product);  // Returns all stock entries for a product

    // Optional: Find stock by product and shelf
    Optional<Stock> findByProductAndShelf(Product product, Shelf shelf);
}
