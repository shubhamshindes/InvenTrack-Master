package com.app.inven.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.inven.dao.SupplierRepository;
import com.app.inven.pojo.Supplier;

	@Service
	
	public class SupplierServiceImpl implements SupplierService {
		@Autowired
		private SupplierRepository supplierRepository;
	
		@Override
		public Supplier save(Supplier supplier) {
			return supplierRepository.save(supplier);
		}
	
		@Override
		public List<Supplier> getAll() {
			return supplierRepository.findAll();
		}
	
		   @Override
		    public Supplier getById(Long id) {
		        return supplierRepository.findById(id)
		                .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + id));
		    }


	@Override
	public Supplier update(Long id, Supplier supplier) {
		Supplier existing = supplierRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Supplier not found"));
		existing.setSupplierName(supplier.getSupplierName());
		existing.setContactNumber(supplier.getEmail());
		existing.setContactNumber(supplier.getContactNumber());
		return supplierRepository.save(existing);
	}

	@Override
	public void delete(Long id) {
		supplierRepository.deleteById(id);
	}
}
