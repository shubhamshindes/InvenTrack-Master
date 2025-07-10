package com.app.inven.service;

import com.app.inven.DTO.InwardEntryDTO;
import com.app.inven.pojo.PurchaseOrder;

import java.util.List;

public interface PurchaseOrderService {
    PurchaseOrder createOrder(Long supplierId, List<InwardEntryDTO> entries);
    List<PurchaseOrder> getAllOrders();
    PurchaseOrder getOrderById(Long id);
    PurchaseOrder updateStatus(Long id, String status);
    void deleteOrder(Long id);
}
