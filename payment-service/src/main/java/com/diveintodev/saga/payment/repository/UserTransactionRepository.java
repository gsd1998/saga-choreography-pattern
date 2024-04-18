package com.diveintodev.saga.payment.repository;

import com.diveintodev.saga.payment.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTransactionRepository extends JpaRepository<UserTransaction,Integer> {
}
