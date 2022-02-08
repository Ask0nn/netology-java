package com.aks0n.controllers;

import com.aks0n.models.Orders;
import com.aks0n.services.OrdersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products/")
public class OrdersController {
    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping("fetch-product")
    public List<Orders> fetchOrders(@RequestParam String name) {
        return ordersService.fetchOrders(name);
    }
}

