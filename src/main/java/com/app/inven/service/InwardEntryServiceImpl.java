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
            entry.setReceivedDate(dto.getReceivedDate() != null ? dto.getReceivedDate() : LocalDate.now());

            // 🔁 Update stock after inward - now handling multiple stock entries
            List<Stock> stocks = stockRepository.findByProduct(product);

            if (stocks.isEmpty()) {
                throw new ResourceNotFoundException("No stock entries found for product: " + product.getProductName());
            }

            // Business logic decision needed here:
            // Option 1: Update first stock entry (simple approach)
            Stock stockToUpdate = stocks.get(0);
            stockToUpdate.addQuantity(dto.getQuantity());
            stockRepository.save(stockToUpdate);

            // OR Option 2: Distribute quantity across multiple stock entries
            // distributeQuantityAcrossStocks(stocks, dto.getQuantity());

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