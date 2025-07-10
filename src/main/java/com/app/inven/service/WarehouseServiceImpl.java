package com.app.inven.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.inven.dao.WarehouseRepository;
import com.app.inven.pojo.Warehouse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
	@Autowired
    private  WarehouseRepository warehouseRepository;

    @Override
    public Warehouse save(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    @Override
    public List<Warehouse> getAll() {
        return warehouseRepository.findAll();
    }

    @Override
    public Optional<Warehouse> getById(Long id) {
        return warehouseRepository.findById(id);
    }

    @Override
    public Warehouse update(Long id, Warehouse warehouse) {
        Warehouse existing = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        existing.setName(warehouse.getName());
        existing.setLocation(warehouse.getLocation());
        return warehouseRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        warehouseRepository.deleteById(id);
    }
}
