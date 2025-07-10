package com.app.inven.service;

import java.util.List;
import java.util.Optional;

import com.app.inven.pojo.Shelf;

import java.util.List;
import java.util.Optional;


public interface ShelfService {
	public Shelf addShelf(Shelf shelf, Long warehouseId);
    List<Shelf> getAllShelves();
    Shelf getShelfById(Long id);
    Shelf updateShelf(Long id, Shelf shelf);
    boolean deleteShelf(Long id);
    void addMultipleShelves(List<Shelf> shelves);
}