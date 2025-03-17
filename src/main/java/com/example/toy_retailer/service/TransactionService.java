package com.example.toy_retailer.service;

import com.example.toy_retailer.entity.Transaction;
import com.example.toy_retailer.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public String processTransactions(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            if (!isValidTransaction(transaction)) {
                return "Invalid transaction data for ID: " + transaction.getPosId();
            }
        }
        transactionRepository.saveAll(transactions);
        return "Transactions processed and saved successfully!";
    }

    private boolean isValidTransaction(Transaction transaction) {
        // Ensure transaction amount and quantity are valid.
        return transaction.getAmount() != null && transaction.getAmount().compareTo(BigDecimal.ZERO) > 0
                && transaction.getQuantity() != null && transaction.getQuantity() > 0;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
