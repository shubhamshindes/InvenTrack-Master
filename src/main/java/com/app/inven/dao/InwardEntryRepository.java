package com.app.inven.dao;

import com.app.inven.pojo.InwardEntry;
import com.app.inven.pojo.Product;
import com.app.inven.pojo.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InwardEntryRepository extends JpaRepository<InwardEntry, Long> {
    List<InwardEntry> findByProduct(Product product);
    List<InwardEntry> findByPurchaseOrder(PurchaseOrder order);
}
