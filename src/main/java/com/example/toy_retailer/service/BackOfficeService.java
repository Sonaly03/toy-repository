package com.example.toy_retailer.service;


import com.example.toy_retailer.entity.Transaction;
import org.springframework.stereotype.Service;

@Service
public class BackOfficeService {
    public void transmitTransaction(Transaction transaction) {
        // Simulate real-time transmission to back-office vendor
        System.out.println("Real-time transmission to Back Office: " + transaction);
    }
}
