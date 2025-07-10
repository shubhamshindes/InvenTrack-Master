package com.app.inven.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.inven.exception.ResourceNotFoundException;
import com.app.inven.dao.ShelfRepository;
import com.app.inven.dao.WarehouseRepository;
import com.app.inven.pojo.Shelf;
import com.app.inven.pojo.Warehouse;

import jakarta.transaction.Transactional;

@Service
public class ShelfServiceImpl implements ShelfService {

    @Autowired
    private ShelfRepository shelfRepository;
    
    @Autowired
    private WarehouseRepository warehouseRepo;

    
    public Shelf addShelf(Shelf shelf, Long warehouseId) {
        Warehouse warehouse = warehouseRepo.findById(warehouseId)
                               .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found"));
        shelf.setWarehouse(warehouse); // Important
        return shelfRepository.save(shelf);
    }

    @Override
    public List<Shelf> getAllShelves() {
        return shelfRepository.findAll();
    }

    @Override
    @Transactional //This keeps the Hibernate session open while the stocks list is being initialized.
   public Shelf getShelfById(Long id) {
        return shelfRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shelf not found with ID: " + id));
    }

    @Override
    public Shelf updateShelf(Long id, Shelf shelfDetails) {
        Optional<Shelf> optionalShelf = shelfRepository.findById(id);
        if (optionalShelf.isPresent()) {
            Shelf existingShelf = optionalShelf.get();
            existingShelf.setShelfName(shelfDetails.getShelfName());
            existingShelf.setLocation(shelfDetails.getLocation());
            // Set other fields as needed
            return shelfRepository.save(existingShelf);
        } else {
            return null;
        }
    }


    @Override
    public boolean deleteShelf(Long id) {
        if (shelfRepository.existsById(id)) {
            shelfRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void addMultipleShelves(List<Shelf> shelves) {
        shelfRepository.saveAll(shelves);
    }
}
