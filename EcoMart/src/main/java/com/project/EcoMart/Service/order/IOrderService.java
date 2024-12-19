package com.project.EcoMart.Service.order;

import com.project.EcoMart.Model.Order;

public interface IOrderService {

	Order placeOrder(Long userId);

	Order getOrder(Long orderId);
}
