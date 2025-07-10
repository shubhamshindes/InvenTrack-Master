package com.app.inven.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.inven.pojo.Shelf;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    // Custom query methods can be added here if needed.
	
	 List<Shelf> findByWarehouse_Name(String warehouseName);

	    // Custom method to find a shelf by its ID
	    Shelf findByShelfId(Long shelfId);
	

}

