package com.example.toy_retailer.controller;

import com.example.toy_retailer.entity.Transaction;
import com.example.toy_retailer.scheduler.BatchJobLauncher;
import com.example.toy_retailer.service.TransactionService;
import com.example.toy_retailer.service.BackOfficeService;
import com.example.toy_retailer.service.SalesTeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
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
    public ResponseEntity<String> receiveTransactions(@Valid @RequestBody List<Transaction> transactions) {
        try {
            System.out.println("Received transactions: " + transactions);
            transactionService.processTransactions(transactions);
            return new ResponseEntity<>("Transactions received and processed successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to process transactions: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/send-to-sales-team")
    public ResponseEntity<String> sendToSalesTeam() {
        try {
            // Trigger batch job to send data to the Sales Team
            batchJobLauncher.runJob();
            return new ResponseEntity<>("Batch job to send data to Sales Team started successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to start the batch job: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/generate-report")
    public ResponseEntity<String> generateReport() {
        try {
            salesTeamService.generateAndSendReport();
            return ResponseEntity.ok("Report generated and sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error generating report: " + e.getMessage());
        }
    }

    @PostMapping("/send-to-back-office")
    public ResponseEntity<String> sendToBackOffice(@RequestBody Transaction transaction) {
        try {
            backOfficeService.sendTransactionToBackOffice(transaction);
            return ResponseEntity.ok("Transaction sent to Back Office successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send transaction to Back Office: " + e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        try {
            List<Transaction> transactions = transactionService.getAllTransactions();

            if (transactions.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
