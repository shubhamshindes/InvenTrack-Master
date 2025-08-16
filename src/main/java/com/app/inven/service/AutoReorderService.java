package com.app.inven.service;

import com.app.inven.DTO.InwardEntryDTO;
import com.app.inven.dao.ProductRepository;
import com.app.inven.pojo.Product;
import com.app.inven.pojo.PurchaseOrder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutoReorderService {
    private final ProductRepository productRepository;
    private final PurchaseOrderService purchaseOrderService;
//    private final EmailService emailService;

//    @Scheduled(cron = "${app.reorder.cron:0 0 9 * * *}") // Default: daily at 9 AM
    @Scheduled(cron = "${app.reorder.cron:0 0/1 8-21 * * *}")// Every 1 mins from 8 AM to 9 PM
    @Transactional
    public void processAutoReorder() {
        log.info("Starting auto-reorder process");

        productRepository.findProductsNeedingReorder().forEach(product -> {
            try {
                createReorderPurchaseOrder(product);
            } catch (Exception e) {
                log.error("Failed to process reorder for product {}: {}",
                        product.getProductId(), e.getMessage());
            }
        });
    }

    private void createReorderPurchaseOrder(Product product) {
        if (product.getSupplier() == null) {
            throw new IllegalStateException("No supplier assigned for product: " + product.getProductName());
        }

        InwardEntryDTO entry = new InwardEntryDTO();
        entry.setProductId(product.getProductId());
        entry.setQuantity(product.getReorderQuantity());

        PurchaseOrder order = purchaseOrderService.createOrder(
                product.getSupplier().getSupplierId(),
                List.of(entry)
        );

        log.info("Created PO {} for product {}", order.getOrderId(), product.getProductName());
//        emailService.sendReorderNotification(product, order);
    }
}