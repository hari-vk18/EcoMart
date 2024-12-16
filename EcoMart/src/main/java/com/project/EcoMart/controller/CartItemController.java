package com.project.EcoMart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.EcoMart.Service.cart.ICartItemService;
import com.project.EcoMart.exceptions.ResourceNotFoundException;
import com.project.EcoMart.response.ApiResponse;

@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

	@Autowired
	private ICartItemService cartItemService;

	@PostMapping("/item/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long cardId, @RequestParam Long productId,
			@RequestParam int quantity) {
		try {
			cartItemService.addItemToCart(cardId, productId, quantity);
			return ResponseEntity.ok(new ApiResponse("Add success!", null));

		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@DeleteMapping("/{cartId}/item/{itemId}/remove")
	public ResponseEntity<ApiResponse> removeItemFromCart(Long cartId, Long itemId) {
		try {
			cartItemService.removeItemFromCart(cartId, itemId);
			return ResponseEntity.ok(new ApiResponse("Remove item success!", null));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

}
