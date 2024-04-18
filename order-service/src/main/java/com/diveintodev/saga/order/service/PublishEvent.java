package com.diveintodev.saga.order.service;

import com.diveintodev.saga.commons.dto.OrderRequestDto;
import com.diveintodev.saga.commons.event.OrderEvent;
import com.diveintodev.saga.commons.event.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class PublishEvent {

    @Autowired
    private Sinks.Many<OrderEvent> sinks;

    public void publishOrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
        OrderEvent orderEvent = new OrderEvent(orderRequestDto,orderStatus);
        sinks.tryEmitNext(orderEvent);
    }
}
