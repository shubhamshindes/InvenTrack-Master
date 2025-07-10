package com.app.inven.dao;

import com.app.inven.pojo.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


    public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
        List<PurchaseOrder> findByStatus(String status);
    }

