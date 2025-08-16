package com.app.inven.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.inven.pojo.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom query methods (if needed) can be added here
    Product findByProductCode(String productCode); // Example: Fetch product by productCode
    List<Product> findByProductName(String productName);
    boolean existsByProductCode(String productCode);
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.stocks")
    List<Product> findAllWithStocks();
    @Query("SELECT p FROM Product p JOIN p.stocks s WHERE s.quantity <= p.reorderThreshold AND p.isAutoReorder = true")
    List<Product> findProductsNeedingReorder();

    @Query("SELECT p FROM Product p JOIN FETCH p.supplier WHERE p.productId = :id")
    Optional<Product> findByIdWithSupplier(@Param("id") Long id);
}
