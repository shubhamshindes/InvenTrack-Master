package com.app.inven.dao;

import com.app.inven.pojo.InwardEntry;
import com.app.inven.pojo.Product;
import com.app.inven.pojo.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InwardEntryRepository extends JpaRepository<InwardEntry, Long> {
//    List<InwardEntry> findByProduct(Product product);
    List<InwardEntry> findByPurchaseOrder(PurchaseOrder order);
    @Query("SELECT i FROM InwardEntry i WHERE i.product = :product")
    List<InwardEntry> findByProduct(@Param("product") Product product);
}
