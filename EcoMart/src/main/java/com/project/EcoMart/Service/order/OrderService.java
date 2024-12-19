package com.project.EcoMart.Service.order;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.EcoMart.Model.Order;
import com.project.EcoMart.Model.OrderItem;
import com.project.EcoMart.exceptions.ResourceNotFoundException;
import com.project.EcoMart.repository.OrderRepository;

@Service
public class OrderService implements IOrderService {

	private OrderRepository orderRepo;

	@Override
	public Order placeOrder(Long userId) {
		// TODO Auto-generated method stub

		return null;
	}

	private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
		return orderItemList.stream().map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	public Order getOrder(Long orderId) {
		// TODO Auto-generated method stub
		return orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not exist"));
	}

}
