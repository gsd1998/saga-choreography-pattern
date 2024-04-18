package com.diveintodev.saga.payment.service;

import com.diveintodev.saga.commons.dto.OrderRequestDto;
import com.diveintodev.saga.commons.dto.PaymentRequestDto;
import com.diveintodev.saga.commons.event.OrderEvent;
import com.diveintodev.saga.commons.event.PaymentEvent;
import com.diveintodev.saga.commons.event.PaymentStatus;
import com.diveintodev.saga.payment.entity.UserBalance;
import com.diveintodev.saga.payment.entity.UserTransaction;
import com.diveintodev.saga.payment.repository.UserBalanceRepository;
import com.diveintodev.saga.payment.repository.UserTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PaymentService {

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private UserTransactionRepository userTransactionRepository;

    @PostConstruct
    public void initUserBalanceDb(){
        userBalanceRepository.saveAll(
                Stream.of(new UserBalance(101,5000),
                          new UserBalance(102,3000),
                          new UserBalance(103, 4200),
                          new UserBalance(104, 20000),
                          new UserBalance(105, 999)).collect(Collectors.toList()));
    }

    @Transactional
    public PaymentEvent proceedOrderForPayment(OrderEvent orderEvent) {

        OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto(orderRequestDto.getOrderId(),orderRequestDto.getUserId(),orderRequestDto.getAmount());

        return userBalanceRepository.findById(orderRequestDto.getUserId())
                .filter(userBal -> userBal.getTotalAmount() > orderRequestDto.getAmount())
                .map(userBal -> {
                    userBal.setTotalAmount(userBal.getTotalAmount() - orderRequestDto.getAmount());
                    userTransactionRepository.save(new UserTransaction(orderRequestDto.getOrderId(),orderRequestDto.getUserId(),orderRequestDto.getAmount()));
                    return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);
                }).orElse(new PaymentEvent(paymentRequestDto,PaymentStatus.PAYMENT_FAILED));
    }

    @Transactional
    public void cancelOrder(OrderEvent orderEvent) {
        userTransactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId())
                .ifPresent(userTransaction -> {
                    userTransactionRepository.delete(userTransaction);
                    userBalanceRepository.findById(userTransaction.getUserId())
                            .ifPresent(userBal -> userBal.setTotalAmount(userBal.getTotalAmount() + userTransaction.getAmount()));
                });
    }
}
