package com.project.EcoMart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.EcoMart.Model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
