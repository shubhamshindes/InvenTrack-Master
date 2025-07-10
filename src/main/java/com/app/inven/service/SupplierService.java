package com.app.inven.service;

import java.util.List;

import com.app.inven.pojo.Supplier;

public interface SupplierService {
    Supplier save(Supplier supplier);
    List<Supplier> getAll();
    Supplier getById(Long id);
    Supplier update(Long id, Supplier supplier);
    void delete(Long id);
}
