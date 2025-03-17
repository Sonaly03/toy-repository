package com.example.toy_retailer.service;


import com.example.toy_retailer.entity.Transaction;
import org.springframework.stereotype.Service;

@Service
public class BackOfficeService {

    // Method to handle sending transaction data to the back office
    public void sendTransactionToBackOffice(Transaction transaction) {
        // Validate transaction data
        if (transaction == null || transaction.getId() == null) {
            throw new IllegalArgumentException("Invalid transaction data.");
        }

        System.out.println("Sending transaction to Back Office: " + transaction);
    }
}

