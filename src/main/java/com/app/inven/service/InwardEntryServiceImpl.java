package com.app.inven.service;

import com.app.inven.DTO.InwardEntryDTO;
import com.app.inven.dao.InwardEntryRepository;
import com.app.inven.dao.ProductRepository;
import com.app.inven.dao.PurchaseOrderRepository;
import com.app.inven.dao.StockRepository;
import com.app.inven.exception.ResourceNotFoundException;
import com.app.inven.pojo.InwardEntry;
import com.app.inven.pojo.Product;
import com.app.inven.pojo.PurchaseOrder;
import com.app.inven.pojo.Stock;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InwardEntryServiceImpl implements InwardEntryService {

    private final Logger logger = LoggerFactory.getLogger(InwardEntryServiceImpl.class); // Added logging
    private final InwardEntryRepository inwardEntryRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    @Override
    public InwardEntry addEntry(Long orderId, InwardEntryDTO dto) {
        logger.info("Adding inward entry for order ID: {} and product ID: {}", orderId, dto.getProductId());

        PurchaseOrder order = purchaseOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        InwardEntry entry = new InwardEntry();
        entry.setPurchaseOrder(order);
        entry.setProduct(product);
        entry.setQuantity(dto.getQuantity());
        entry.setReceivedDate(LocalDate.now());

        // 🔁 Update stock after inward
        Stock stock = stockRepository.findByProduct(product)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found for product: " + product.getProductName()));

        stock.addQuantity(dto.getQuantity()); // Update quantity & timestamp
        stockRepository.save(stock); // Persist updated stock

        return inwardEntryRepository.save(entry);

    }

    @Override
    public List<InwardEntry> getEntriesByProduct(Long productId) {
        logger.info("Fetching inward entries by product ID: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return inwardEntryRepository.findByProduct(product);
    }

    @Override
    public List<InwardEntry> getEntriesByOrder(Long orderId) {
        logger.info("Fetching inward entries by order ID: {}", orderId);
        PurchaseOrder order = purchaseOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return inwardEntryRepository.findByPurchaseOrder(order);
    }
}