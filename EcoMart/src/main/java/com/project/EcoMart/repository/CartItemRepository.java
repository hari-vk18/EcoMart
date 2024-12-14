package com.project.EcoMart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.EcoMart.Model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	void deleteAllByCartId(Long id);

}
