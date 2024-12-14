package com.project.EcoMart.Service.cart;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.EcoMart.Model.Cart;
import com.project.EcoMart.exceptions.ResourceNotFoundException;
import com.project.EcoMart.repository.CartItemRepository;
import com.project.EcoMart.repository.CartRepository;

@Service
public class CartService implements ICartService {

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Override
	public Cart getCart(Long id) {
		// TODO Auto-generated method stub
		Cart cart = cartRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart Not found!"));
		BigDecimal totalAmount = cart.getTotalAmount();
		cart.setTotalAmount(totalAmount);
		return cartRepo.save(cart);
	}

	@Override
	public void cleatCart(Long id) {
		// TODO Auto-generated method stub
		Cart cart = getCart(id);
		cartItemRepository.deleteAllByCartId(id);
		cart.getItems().clear();
		cartRepo.deleteById(id);

	}

	@Override
	public BigDecimal getTotalPrice(Long id) {
		// TODO Auto-generated method stub
		Cart cart = getCart(id);
		return cart.getTotalAmount();
	}

}
