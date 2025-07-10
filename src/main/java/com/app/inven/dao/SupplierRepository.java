package com.app.inven.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.inven.pojo.Supplier;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findBySupplierName(String name);
}
