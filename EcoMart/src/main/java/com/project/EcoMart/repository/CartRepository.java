package com.project.EcoMart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.EcoMart.Model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
