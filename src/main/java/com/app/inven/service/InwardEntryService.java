package com.app.inven.service;

import com.app.inven.DTO.InwardEntryDTO;
import com.app.inven.pojo.InwardEntry;

import java.util.List;

public interface InwardEntryService {
    InwardEntry addEntry(Long orderId, InwardEntryDTO dto);
    List<InwardEntry> getEntriesByProduct(Long productId);
    List<InwardEntry> getEntriesByOrder(Long orderId);

}
