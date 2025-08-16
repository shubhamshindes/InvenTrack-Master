package com.app.inven.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.app.inven.DTO.ProductReorderConfigDTO;
import com.app.inven.dao.ProductRepository;
import com.app.inven.exception.ResourceNotFoundException;
import com.app.inven.service.*;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.inven.DTO.ProductRequestDTO;
import com.app.inven.pojo.Product;
import com.app.inven.pojo.Shelf;
import com.app.inven.pojo.Stock;
import com.app.inven.pojo.Supplier;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;
	private final SupplierService supplierService;
	private final ShelfService shelfService;
	private final StockService stockService;
	private final ProductRepository productRepository;
	private final AutoReorderService autoReorderService;
	// Create Product with Supplier and Initial Stock
	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody ProductRequestDTO productDTO) {

		Supplier supplier = supplierService.getById(productDTO.getSupplierId());
		Shelf shelf = shelfService.getShelfById(productDTO.getShelfId());

		Product product = new Product();
		product.setProductName(productDTO.getProductName());
		product.setProductCode(productDTO.getProductCode());
		product.setPrice(productDTO.getPrice());
		product.setProductCategory(productDTO.getProductCategory());
		product.setManufactureDate(productDTO.getManufactureDate());
		product.setExpiryDate(productDTO.getExpiryDate());
		product.setProductDescription(productDTO.getProductDescription());
		product.setImageUrl(productDTO.getImageUrl());
		product.setSupplier(supplier);

		Product savedProduct = productService.save(product);

		// Create stock entry
		Stock stock = new Stock();
		stock.setProduct(savedProduct);
		stock.setShelf(shelf);
		stock.setQuantity(productDTO.getInitialQuantity());
		stock.setLastUpdated(LocalDateTime.now());

		stockService.save(stock);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
	}

	// ✅ Get all products with stocks initialized
	@GetMapping
	@Transactional
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.findAll();
		for (Product p : products) {
			Hibernate.initialize(p.getStocks()); // <-- forces lazy collection initialization
		}
		return ResponseEntity.ok(products);
	}

	// Get product by ID
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		Product product = productService.findById(id);
		Hibernate.initialize(product.getStocks()); // optional: force init when needed
		return ResponseEntity.ok(product);
	}

	// Update product details
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productDTO) {
		Product updated = productService.updateProduct(id, productDTO);
		return ResponseEntity.ok(updated);
	}

	// Delete product
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	// Get all stock locations for a product
	@GetMapping("/{productId}/stocks")
	public ResponseEntity<List<Stock>> getStockByProduct(@PathVariable Long productId) {
		return ResponseEntity.ok(stockService.getStockByProductId(productId));
	}

	@PutMapping("/reorder-config")
	public ResponseEntity<Product> updateReorderConfig(
			@RequestBody ProductReorderConfigDTO config) {
		Product product = productRepository.findById(config.getProductId())
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		product.setReorderThreshold(config.getReorderThreshold());
		product.setReorderQuantity(config.getReorderQuantity());
		product.setIsAutoReorder(config.getIsAutoReorder());

		return ResponseEntity.ok(productRepository.save(product));
	}

	@GetMapping("/needing-reorder")
	public ResponseEntity<List<Product>> getProductsNeedingReorder() {
		return ResponseEntity.ok(productRepository.findProductsNeedingReorder());
	}

	@PostMapping("/trigger-reorder")
	public ResponseEntity<String> manualTriggerReorder() {
		autoReorderService.processAutoReorder();
		return ResponseEntity.ok("Manual reorder triggered");
	}
}
