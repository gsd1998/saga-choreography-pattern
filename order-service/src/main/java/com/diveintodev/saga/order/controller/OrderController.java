package com.diveintodev.saga.order.controller;


import com.diveintodev.saga.commons.dto.OrderRequestDto;
import com.diveintodev.saga.order.entity.PurchaseOrder;
import com.diveintodev.saga.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public PurchaseOrder createOrders(@RequestBody OrderRequestDto orderRequestDto){
        return orderService.createOrder(orderRequestDto);
    }

    @GetMapping
    public List<PurchaseOrder> getAllOrders(){
        return orderService.getAllOrders();
    }
}
