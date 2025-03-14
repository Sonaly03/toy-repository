package com.example.toy_retailer.controller;

import com.example.toy_retailer.entity.Transaction;
import com.example.toy_retailer.service.BackOfficeService;
import com.example.toy_retailer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController
{

    @Autowired
    private TransactionService posService;

    @Autowired
    private BackOfficeService backOfficeService;

    @PostMapping("/receive")
    public ResponseEntity<String> receiveTransaction(@RequestBody Transaction transaction) {
        posService.saveTransaction(transaction);
        backOfficeService.transmitTransaction(transaction); // Real-time transmission
        return new ResponseEntity<>("Transaction saved and transmitted successfully", HttpStatus.CREATED);
    }
}
