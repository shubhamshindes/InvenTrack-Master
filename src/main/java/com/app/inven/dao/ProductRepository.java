package com.app.inven.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.inven.pojo.Product;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom query methods (if needed) can be added here
    Product findByProductCode(String productCode); // Example: Fetch product by productCode
    List<Product> findByProductName(String productName);
    boolean existsByProductCode(String productCode);
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.stocks")
    List<Product> findAllWithStocks();
}
