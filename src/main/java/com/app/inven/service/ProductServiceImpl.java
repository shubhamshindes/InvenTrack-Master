package com.app.inven.service;

import java.util.List;

import com.app.inven.exception.ProductAlreadyExistsException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.inven.exception.ResourceNotFoundException;
import com.app.inven.DTO.ProductRequestDTO;
import com.app.inven.dao.ProductRepository;
import com.app.inven.pojo.Product;
import com.app.inven.pojo.Supplier;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private SupplierService supplierService;

	@Transactional
	public Product save(Product product) throws ProductAlreadyExistsException {
		// Check if product with the same code already exists
		if (productRepository.existsByProductCode(product.getProductCode())) {
			throw new ProductAlreadyExistsException("Product with code " + product.getProductCode() + " already exists.");
		}
		return productRepository.save(product);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAllWithStocks(); // Fetches stocks eagerly
	}

	@Override
	public Product findById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
	}

	@Override
	public Product updateProduct(Long id, ProductRequestDTO dto) {
		Product existing = findById(id);
		existing.setProductName(dto.getProductName());
		existing.setProductCode(dto.getProductCode());
		existing.setPrice(dto.getPrice());
		existing.setProductCategory(dto.getProductCategory());
		existing.setManufactureDate(dto.getManufactureDate());
		existing.setExpiryDate(dto.getExpiryDate());
		existing.setProductDescription(dto.getProductDescription());
		existing.setImageUrl(dto.getImageUrl());

		Supplier supplier = supplierService.getById(dto.getSupplierId());
		existing.setSupplier(supplier);
		return productRepository.save(existing);
	}

	@Override
	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}
}
