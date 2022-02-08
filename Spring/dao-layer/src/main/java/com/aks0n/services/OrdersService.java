package com.aks0n.services;

import com.aks0n.models.Orders;
import com.aks0n.repositories.OrdersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {
    private final OrdersRepository ordersRepository;

    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public List<Orders> fetchOrders(String name) {
        return ordersRepository.getProductsByName(name);
    }
}
