package com.app.inven.service;

import java.util.List;
import java.util.Optional;

import com.app.inven.pojo.Warehouse;

public interface WarehouseService {
    Warehouse save(Warehouse warehouse);
    List<Warehouse> getAll();
    Optional<Warehouse> getById(Long id);
    Warehouse update(Long id, Warehouse warehouse);
    void delete(Long id);
}
