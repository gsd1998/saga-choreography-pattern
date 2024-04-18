package com.diveintodev.saga.order.entity;

import com.diveintodev.saga.commons.event.OrderStatus;
import com.diveintodev.saga.commons.event.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "purchase_order_tbl")
public class PurchaseOrder {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer amount;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

}
