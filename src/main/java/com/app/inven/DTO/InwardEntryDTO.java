package com.app.inven.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class InwardEntryDTO {
    private Long entryId;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private LocalDate receivedDate;



}
