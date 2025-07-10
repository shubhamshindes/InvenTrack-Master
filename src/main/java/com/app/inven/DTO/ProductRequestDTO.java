package com.app.inven.DTO;

import java.time.LocalDate;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequestDTO {
    private String productName;
    private String productCode;
    private Double price;
    private String productCategory;
    private LocalDate manufactureDate;
    private LocalDate expiryDate;
    private String productDescription;
    private String imageUrl;
    private Long supplierId;
    private Long shelfId;
    private Integer initialQuantity;

    
    
}
