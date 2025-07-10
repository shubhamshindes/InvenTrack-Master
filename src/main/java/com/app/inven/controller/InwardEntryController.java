package com.app.inven.controller;

import com.app.inven.DTO.InwardEntryDTO;
import com.app.inven.pojo.InwardEntry;
import com.app.inven.service.InwardEntryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inward-entries")
@RequiredArgsConstructor
public class InwardEntryController {

    private final InwardEntryService inwardEntryService;
    private final Logger logger = LoggerFactory.getLogger(InwardEntryController.class); // Added logging

    @PostMapping("/add/{orderId}")
    public ResponseEntity<InwardEntry> addEntry(@PathVariable Long orderId,
                                                @RequestBody InwardEntryDTO dto) {
        logger.info("Adding entry to order ID: {}", orderId);
        return ResponseEntity.ok(inwardEntryService.addEntry(orderId, dto));
    }

    @GetMapping("/by-product/{productId}")
    public ResponseEntity<List<InwardEntry>> getByProduct(@PathVariable Long productId) {
        logger.info("Getting entries for product ID: {}", productId);
        return ResponseEntity.ok(inwardEntryService.getEntriesByProduct(productId));
    }

    @GetMapping("/by-order/{orderId}")
    public ResponseEntity<List<InwardEntry>> getByOrder(@PathVariable Long orderId) {
        logger.info("Getting entries for order ID: {}", orderId);
        return ResponseEntity.ok(inwardEntryService.getEntriesByOrder(orderId));
    }
}
