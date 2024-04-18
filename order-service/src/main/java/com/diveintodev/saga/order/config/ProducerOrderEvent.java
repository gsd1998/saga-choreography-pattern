package com.diveintodev.saga.order.config;

import com.diveintodev.saga.commons.event.OrderEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class ProducerOrderEvent {

    @Bean
    public Sinks.Many<OrderEvent> sinks(){
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<OrderEvent>> publishEventToKafka(Sinks.Many<OrderEvent> sinks){
        return sinks::asFlux;
    }


}
