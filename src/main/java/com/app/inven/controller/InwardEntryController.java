package com.app.inven.controller;

import com.app.inven.DTO.InwardEntryDTO;
import com.app.inven.pojo.InwardEntry;
import com.app.inven.service.InwardEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inward-entries")
@RequiredArgsConstructor
public class InwardEntryController {

    private final InwardEntryService inwardEntryService;

    @PostMapping("/add/{orderId}")
    public ResponseEntity<InwardEntry> addEntry(@PathVariable Long orderId,
                                                @RequestBody InwardEntryDTO dto) {
        return ResponseEntity.ok(inwardEntryService.addEntry(orderId, dto));
    }

    @GetMapping("/by-product/{productId}")
    public ResponseEntity<List<InwardEntry>> getByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(inwardEntryService.getEntriesByProduct(productId));
    }

    @GetMapping("/by-order/{orderId}")
    public ResponseEntity<List<InwardEntry>> getByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(inwardEntryService.getEntriesByOrder(orderId));
    }
}
