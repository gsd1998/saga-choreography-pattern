package com.diveintodev.saga.order.config;

import com.diveintodev.saga.commons.event.PaymentEvent;
import com.diveintodev.saga.order.entity.PurchaseOrder;
import com.diveintodev.saga.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.function.Consumer;

@Configuration
public class ConsumerPaymentEvent {

    @Autowired
    private UpdateOrderConfig updateOrderConfig;

    @Autowired
    private OrderRepository orderRepository;

    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer(){
        return (payment)-> updateOrderConfig.updateOrder(payment.getPaymentRequestDto().getOrderId(),po->{
            po.setPaymentStatus(payment.getPaymentStatus());
        });
    }
}
