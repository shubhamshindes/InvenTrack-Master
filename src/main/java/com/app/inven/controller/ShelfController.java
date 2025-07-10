	package com.app.inven.controller;
	
	
	import java.util.List;
	import java.util.Optional;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.DeleteMapping;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.PutMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;

import com.app.inven.pojo.Shelf;
import com.app.inven.service.ShelfService;
	
	@RestController
	@RequestMapping("/api/shelves")
	public class ShelfController {
	
	    @Autowired
	    private ShelfService shelfService;
	
	    // Endpoint to add a new shelf
	    @PostMapping("/add/{warehouseId}")
	    public ResponseEntity<Shelf> addShelf( @RequestBody Shelf shelf, @PathVariable Long warehouseId) {
	        
	        Shelf createdShelf = shelfService.addShelf(shelf, warehouseId);
	        return ResponseEntity.ok(createdShelf);
	    }
	
	    // Endpoint to get all shelves
	    @GetMapping
	    public ResponseEntity<List<Shelf>> getAllShelves() {
	        List<Shelf> shelves = shelfService.getAllShelves();
	        return ResponseEntity.ok(shelves);
	    }
	
	    // Endpoint to get shelf by ID
	    @GetMapping("/{id}")
	    public ResponseEntity<Shelf> getShelfById(@PathVariable("id") Long shelfId) {
	        try {
	            Shelf shelf = shelfService.getShelfById(shelfId);
	            return ResponseEntity.ok(shelf);
	        } catch (RuntimeException e) {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    // Endpoint to update shelf details
	    @PutMapping("/{id}")
	    public ResponseEntity<Shelf> updateShelf(@PathVariable("id") Long shelfId, @RequestBody Shelf shelf) {
	        Shelf updatedShelf = shelfService.updateShelf(shelfId, shelf);
	        return updatedShelf != null ? ResponseEntity.ok(updatedShelf) : ResponseEntity.notFound().build();
	    }
	
	    // Endpoint to delete shelf
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteShelf(@PathVariable("id") Long shelfId) {
	        return shelfService.deleteShelf(shelfId) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	    }
	 // Endpoint to add multiple shelves
	    @PostMapping("/addMultiple")
	    public String addMultipleShelves(@RequestBody List<Shelf> shelves) {
	        shelfService.addMultipleShelves(shelves);
	        return "Shelves added successfully!";
	    }
	    
	}
