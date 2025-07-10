package com.app.inven.service;

import com.app.inven.DTO.InwardEntryDTO;
import com.app.inven.dao.InwardEntryRepository;
import com.app.inven.dao.ProductRepository;
import com.app.inven.dao.PurchaseOrderRepository;
import com.app.inven.exception.ResourceNotFoundException;
import com.app.inven.pojo.InwardEntry;
import com.app.inven.pojo.Product;
import com.app.inven.pojo.PurchaseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InwardEntryServiceImpl implements InwardEntryService {

    private final InwardEntryRepository inwardEntryRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;

    @Override
    public InwardEntry addEntry(Long orderId, InwardEntryDTO dto) {
        PurchaseOrder order = purchaseOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        InwardEntry entry = new InwardEntry();
        entry.setPurchaseOrder(order);
        entry.setProduct(product);
        entry.setQuantity(dto.getQuantity());
        entry.setReceivedDate(LocalDate.now());

        return inwardEntryRepository.save(entry);
    }

    @Override
    public List<InwardEntry> getEntriesByProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return inwardEntryRepository.findByProduct(product);
    }

    @Override
    public List<InwardEntry> getEntriesByOrder(Long orderId) {
        PurchaseOrder order = purchaseOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return inwardEntryRepository.findByPurchaseOrder(order);
    }
}
