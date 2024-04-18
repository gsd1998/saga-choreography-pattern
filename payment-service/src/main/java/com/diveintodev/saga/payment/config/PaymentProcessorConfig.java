package com.diveintodev.saga.payment.config;

import com.diveintodev.saga.commons.event.OrderEvent;
import com.diveintodev.saga.commons.event.OrderStatus;
import com.diveintodev.saga.commons.event.PaymentEvent;
import com.diveintodev.saga.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class PaymentProcessorConfig {

    @Autowired
    private PaymentService paymentService;

    @Bean
    public Function<Flux<OrderEvent>,Flux<PaymentEvent>> paymentProcessor(){
       return orderEventFlux -> orderEventFlux.flatMap(this::processPayment);
    }

    private Mono<PaymentEvent> processPayment(OrderEvent orderEvent) {
        if(orderEvent.getOrderStatus().equals(OrderStatus.ORDER_CREATED)){
            return Mono.fromSupplier(() -> this.paymentService.proceedOrderForPayment(orderEvent));
        }else{
            return Mono.fromRunnable(() -> this.paymentService.cancelOrder(orderEvent));
        }
    }


}
