package com.diveintodev.saga.order.service;

import com.diveintodev.saga.commons.dto.OrderRequestDto;
import com.diveintodev.saga.commons.event.OrderStatus;
import com.diveintodev.saga.commons.event.PaymentStatus;
import com.diveintodev.saga.order.config.UpdateOrderConfig;
import com.diveintodev.saga.order.entity.PurchaseOrder;
import com.diveintodev.saga.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private PublishEvent publishEvent;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UpdateOrderConfig updateOrderConfig;

    @Transactional
    public PurchaseOrder createOrder(OrderRequestDto orderRequestDto) {
        PurchaseOrder order = orderRepository.save(convertDtoToEntity(orderRequestDto));
        orderRequestDto.setOrderId(order.getId());
        publishEvent.publishOrderEvent(orderRequestDto, OrderStatus.ORDER_CREATED);

        return order;
    }

    public List<PurchaseOrder> getAllOrders(){
        return orderRepository.findAll();
    }

    private PurchaseOrder convertDtoToEntity(OrderRequestDto dto){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(dto.getUserId());
        purchaseOrder.setProductId(dto.getProductId());
        purchaseOrder.setAmount(dto.getAmount());
        purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
        purchaseOrder.setPaymentStatus(PaymentStatus.PAYMENT_PROCESSING);
        return purchaseOrder;
    }
}
