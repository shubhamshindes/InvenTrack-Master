package com.app.inven.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockDashboardDTO {
    private Long productId;
    private String productName;
    private Integer quantity;
    private boolean needsReorder;
}
