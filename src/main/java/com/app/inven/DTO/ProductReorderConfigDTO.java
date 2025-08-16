package com.app.inven.DTO;

import lombok.Data;

@Data
public class ProductReorderConfigDTO {
    private Long productId;
    private Integer reorderThreshold;
    private Integer reorderQuantity;
    private Boolean isAutoReorder;
}