package com.example.toy_retailer.repository;

import com.example.toy_retailer.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepository  extends JpaRepository<Transaction, Long> {
}
