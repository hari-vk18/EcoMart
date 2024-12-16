package com.project.EcoMart.Service.cart;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.EcoMart.Model.Cart;
import com.project.EcoMart.Model.CartItem;
import com.project.EcoMart.Model.Product;
import com.project.EcoMart.Service.product.IProductService;
import com.project.EcoMart.exceptions.ResourceNotFoundException;
import com.project.EcoMart.repository.CartItemRepository;
import com.project.EcoMart.repository.CartRepository;

@Service
public class CartItemService implements ICartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private IProductService productService;

	@Autowired
	private ICartService cartService;

	@Override
	public void addItemToCart(Long cartId, Long productId, int quantity) {
		// TODO Auto-generated method stub

		Cart cart = cartService.getCart(cartId);
		Product product = productService.getProductById(productId);

		CartItem cartItem = cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId))
				.findFirst().orElse(new CartItem());

		if (cartItem.getId() == null) {
			cartItem.setCart(cart);
			cartItem.setProduct(product);
			cartItem.setUnitPrice(product.getPrice());
			cartItem.setQuantity(quantity);
		} else {
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
		}

		cartItem.setTotalPrice();

		cartItemRepository.save(cartItem);
		cartRepository.save(cart);

	}

	@Override
	public void removeItemFromCart(Long cartId, Long productId) {
		// TODO Auto-generated method stub

		Cart cart = cartService.getCart(cartId);

		CartItem itemToRemove = getCartItem(cartId, productId);
		cart.removeItem(itemToRemove);
		cartRepository.save(cart);

	}

	@Override
	public void updateItemQuantity(Long cartId, Long productId, int quantity) {
		// TODO Auto-generated method stub
		Cart cart = cartRepository.getById(cartId);

		cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst()
				.ifPresent(item -> {
					item.setQuantity(quantity);
					item.setUnitPrice(item.getProduct().getPrice());
					item.setTotalPrice();
				});

		BigDecimal totalAmount = cart.getTotalAmount();
		cart.setTotalAmount(totalAmount);
		cartRepository.save(cart);
	}

	@Override
	public CartItem getCartItem(Long cartId, Long productId) {
		Cart cart = cartService.getCart(cartId);
		return cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));

	}

}
