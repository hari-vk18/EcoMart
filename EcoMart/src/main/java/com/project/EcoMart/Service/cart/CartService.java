package com.project.EcoMart.Service.cart;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.EcoMart.Model.Cart;
import com.project.EcoMart.exceptions.ResourceNotFoundException;
import com.project.EcoMart.repository.CartItemRepository;
import com.project.EcoMart.repository.CartRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService implements ICartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	private AtomicLong cartIdGenerator = new AtomicLong(0);

	private static final Logger log = LoggerFactory.getLogger(CartService.class);

	@Override
	public Cart getCart(Long id) {
		// TODO Auto-generated method stub
		Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
		BigDecimal totalAmount = cart.getTotalAmount();
		cart.setTotalAmount(totalAmount);
		return cartRepository.save(cart);
	}

	@Override
	public void cleatCart(Long id) {
		// TODO Auto-generated method stub
		Cart cart = getCart(id);
		cartItemRepository.deleteAllByCartId(id);
		cart.getItems().clear();
		cartRepository.deleteById(id);

	}

	@Override
	public BigDecimal getTotalPrice(Long id) {
		Cart cart = getCart(id);
		return cart.getTotalAmount();
	}

	@Transactional
	@Override
	public Long initializeNewCart() {
		Cart newCart = new Cart();
		Long newCartId = cartIdGenerator.incrementAndGet();
		newCart.setId(newCartId);
		cartRepository.save(newCart);
		return newCartId;
	}

}
