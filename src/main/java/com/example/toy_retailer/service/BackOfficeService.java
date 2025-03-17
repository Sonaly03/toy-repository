package com.example.toy_retailer.service;


import com.example.toy_retailer.entity.Transaction;
import org.springframework.stereotype.Service;

@Service
public class BackOfficeService {

    public void sendTransactionToBackOffice(Transaction transaction) {

        if (transaction == null || transaction.getId() == null) {
            throw new IllegalArgumentException("Invalid transaction data.");
        }

        System.out.println("Sending transaction to Back Office: " + transaction);
    }
}

