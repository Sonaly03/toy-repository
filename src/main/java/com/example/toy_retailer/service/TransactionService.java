package com.example.toy_retailer.service;

import com.example.toy_retailer.entity.Transaction;
import com.example.toy_retailer.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
