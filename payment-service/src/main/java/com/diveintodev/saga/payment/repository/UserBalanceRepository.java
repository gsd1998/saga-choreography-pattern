package com.diveintodev.saga.payment.repository;

import com.diveintodev.saga.payment.entity.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBalanceRepository extends JpaRepository<UserBalance,Integer> {
}
