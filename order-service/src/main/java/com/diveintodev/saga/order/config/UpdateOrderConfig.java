package com.diveintodev.saga.order.config;

import com.diveintodev.saga.commons.dto.OrderRequestDto;
import com.diveintodev.saga.commons.event.OrderStatus;
import com.diveintodev.saga.commons.event.PaymentStatus;
import com.diveintodev.saga.order.entity.PurchaseOrder;
import com.diveintodev.saga.order.repository.OrderRepository;
import com.diveintodev.saga.order.service.PublishEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Consumer;

@Configuration
public class UpdateOrderConfig {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PublishEvent publishEvent;

    @Transactional
    public void updateOrder(int orderId, Consumer<PurchaseOrder> purchaseOrder){
        orderRepository.findById(orderId).ifPresent(purchaseOrder.andThen(this::updateOrderResponse));
    }

    private void updateOrderResponse(PurchaseOrder purchaseOrder) {
        boolean isPaymentCompleted = purchaseOrder.getPaymentStatus().equals(PaymentStatus.PAYMENT_COMPLETED);
        OrderStatus orderStatus = isPaymentCompleted ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_FAILED;
        purchaseOrder.setOrderStatus(orderStatus);
        if(!isPaymentCompleted){
            publishEvent.publishOrderEvent(convertEntityToDto(purchaseOrder),orderStatus);
        }
    }



    private OrderRequestDto convertEntityToDto(PurchaseOrder purchaseOrder){
        OrderRequestDto dto = new OrderRequestDto();
        dto.setUserId(purchaseOrder.getUserId());
        dto.setOrderId(purchaseOrder.getId());
        dto.setAmount(purchaseOrder.getAmount());
        dto.setProductId(purchaseOrder.getProductId());
        return dto;
    }

}
