package com.app.inven.service;

import java.time.LocalDateTime;
import java.util.List;

import com.app.inven.dao.ProductRepository;
import com.app.inven.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.inven.dao.StockRepository;
import com.app.inven.exception.ResourceNotFoundException;
import com.app.inven.pojo.Stock;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
	private StockRepository stockRepo;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Stock addStock(Stock stock) {
		Long productId = stock.getProduct().getProductId(); // fetch productId from the stock object
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

		stock.setProduct(product); // set managed entity
		stock.setLastUpdated(LocalDateTime.now());

		return stockRepo.save(stock);
	}

	@Override
	public List<Stock> getAllStocks() {
		return stockRepo.findAll();
	}

	@Override
	public Stock getStockById(Long id) {
		return stockRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Stock not found with ID: " + id));
	}

	@Override
	public Stock updateStock(Long id, Stock stockDetails) {
		Stock existingStock = stockRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Stock not found with ID: " + id));

		existingStock.setQuantity(stockDetails.getQuantity());
		existingStock.setLastUpdated(LocalDateTime.now());

		// Optional: update shelf or product if needed
		return stockRepo.save(existingStock);
	}

	@Override
	public Stock save(Stock stock) {
		return stockRepo.save(stock);
	}

	@Override
	public List<Stock> getStockByProductId(Long productId) {
		return stockRepo.findByProductProductId(productId);
	}

	@Override
	public boolean deleteStock(Long id) {
		if (stockRepo.existsById(id)) {
			stockRepo.deleteById(id);
			return true;
		}
		return false;
	}
}
