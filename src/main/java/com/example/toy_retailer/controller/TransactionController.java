package com.example.toy_retailer.controller;

import com.example.toy_retailer.entity.Transaction;
import com.example.toy_retailer.service.BatchJobLauncher;
import com.example.toy_retailer.service.TransactionService;
import com.example.toy_retailer.service.BackOfficeService;
import com.example.toy_retailer.service.SalesTeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private BatchJobLauncher batchJobLauncher;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private SalesTeamService salesTeamService;
    @Autowired
    private BackOfficeService backOfficeService;

    @PostMapping("/receive-transactions")
    public String receiveTransactions(@Valid @RequestBody List<Transaction> transactions) {
        try {
            // Ensure data integrity and security
            System.out.println("Received transactions: " + transactions);
            transactionService.processTransactions(transactions);
            return "Transactions received and processed successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to process transactions: " + e.getMessage();
        }
    }

    @PostMapping("/send-to-sales-team")
    public String sendToSalesTeam() {
        try {
            // Trigger batch job to send data to the Sales Team
            batchJobLauncher.runJob();
            return "Batch job to send data to Sales Team started successfully!";
        } catch (Exception e) {
           // e.printStackTrace();
            return "Failed to start the batch job: " + e.getMessage();
        }
    }

    @PostMapping("/send-to-back-office")
    public String sendToBackOffice(@RequestBody Transaction transaction) {
        try {
            // Send transaction data in real-time to the Back Office vendor
            backOfficeService.sendTransactionToBackOffice(transaction);
            return "Transaction sent to Back Office successfully!";
        } catch (Exception e) {
          //  e.printStackTrace();
            return "Failed to send transaction to Back Office: " + e.getMessage();
        }
    }
}
