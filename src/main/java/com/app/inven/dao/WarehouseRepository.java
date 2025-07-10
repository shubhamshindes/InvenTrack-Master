package com.app.inven.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.inven.pojo.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
	
	
}
