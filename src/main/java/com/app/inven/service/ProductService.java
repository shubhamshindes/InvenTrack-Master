package com.app.inven.service;



import java.util.List;

import com.app.inven.DTO.ProductRequestDTO;
import com.app.inven.pojo.Product;

public interface ProductService {
    Product save(Product product);
    List<Product> findAll();
    Product findById(Long id);
    Product updateProduct(Long id, ProductRequestDTO dto);
    void deleteById(Long id);

}
