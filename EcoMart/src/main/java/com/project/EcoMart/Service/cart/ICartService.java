package com.project.EcoMart.Service.cart;

import java.math.BigDecimal;

import com.project.EcoMart.Model.Cart;

public interface ICartService {

	Cart getCart(Long id);

	void cleatCart(Long id);

	BigDecimal getTotalPrice(Long id);

	Long initializeNewCart();

	Cart getOrInitializeCart(Long cartId);
}
