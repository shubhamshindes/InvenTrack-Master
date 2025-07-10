package com.app.inven.service;

import com.app.inven.DTO.InwardEntryDTO;
import com.app.inven.dao.PurchaseOrderRepository;
import com.app.inven.dao.SupplierRepository;
import com.app.inven.exception.ResourceNotFoundException;
import com.app.inven.pojo.PurchaseOrder;
import com.app.inven.pojo.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierRepository supplierRepository;

    @Override
    public PurchaseOrder createOrder(Long supplierId, List<InwardEntryDTO> entries) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        PurchaseOrder order = new PurchaseOrder();
        order.setSupplier(supplier);
        order.setOrderDate(LocalDate.now());
        order.setStatus("PENDING");

        return purchaseOrderRepository.save(order);
    }

    @Override
    public List<PurchaseOrder> getAllOrders() {
        return purchaseOrderRepository.findAll();
    }

    @Override
    public PurchaseOrder getOrderById(Long id) {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public PurchaseOrder updateStatus(Long id, String status) {
        PurchaseOrder order = getOrderById(id);
        order.setStatus(status);
        return purchaseOrderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        purchaseOrderRepository.deleteById(id);
    }
}
