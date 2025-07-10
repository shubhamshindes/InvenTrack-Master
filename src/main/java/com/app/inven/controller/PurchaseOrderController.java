package com.app.inven.controller;

import com.app.inven.DTO.InwardEntryDTO;
import com.app.inven.pojo.PurchaseOrder;
import com.app.inven.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    @PostMapping("/create/{supplierId}")
    public ResponseEntity<PurchaseOrder> createOrder(@PathVariable Long supplierId,
                                                     @RequestBody List<InwardEntryDTO> entries) {
        return ResponseEntity.ok(purchaseOrderService.createOrder(supplierId, entries));
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> getAllOrders() {
        return ResponseEntity.ok(purchaseOrderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseOrderService.getOrderById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PurchaseOrder> updateStatus(@PathVariable Long id,
                                                      @RequestParam String status) {
        return ResponseEntity.ok(purchaseOrderService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        purchaseOrderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
