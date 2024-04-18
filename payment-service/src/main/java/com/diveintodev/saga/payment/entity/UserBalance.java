package com.diveintodev.saga.payment.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBalance {

    @Id
    private Integer userId;
    private Integer totalAmount;

}
