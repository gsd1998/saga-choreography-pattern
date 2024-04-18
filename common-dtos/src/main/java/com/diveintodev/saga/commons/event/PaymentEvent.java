package com.diveintodev.saga.commons.event;

import com.diveintodev.saga.commons.dto.PaymentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PaymentEvent implements Event{

    private UUID eventId=UUID.randomUUID();
    private Date eventDate=new Date();

    private PaymentRequestDto paymentRequestDto;

    public PaymentEvent(PaymentRequestDto paymentRequestDto, PaymentStatus paymentStatus) {
        this.paymentRequestDto = paymentRequestDto;
        this.paymentStatus = paymentStatus;
    }

    private PaymentStatus paymentStatus;

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getEventDate() {
        return eventDate;
    }
}
