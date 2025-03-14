package com.example.toy_retailer.service;

import com.example.toy_retailer.entity.Transaction;
import com.example.toy_retailer.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesTeamService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Scheduled(fixedRate = 60000) // Every minute
    public void sendBatchTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        // Simulate sending to Sales Team
        System.out.println("Sending batch transactions to Sales Team: " + transactions);
    }
}