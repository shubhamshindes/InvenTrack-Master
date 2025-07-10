package com.app.inven.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.inven.pojo.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
