
package com.example.toy_retailer.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.toy_retailer.entity.Transaction;
import com.example.toy_retailer.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SalesTeamService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    AmazonS3 amazonS3;


    @Value("${aws.s3.bucket-name}")
    private String s3BucketName;

    // Method to generate and send the report
    public void generateAndSendReport() {
        // Fetch transactions for the report
        List<Transaction> transactions = transactionRepository.findAll();
        String reportPath = "sales_report_" + System.currentTimeMillis() + ".csv";

        // Ensure transactions exist
        if (transactions.isEmpty()) {
            System.out.println("No transactions found to generate the report.");
            return;
        }

        try (FileWriter writer = new FileWriter(reportPath)) {
            // Write headers
            writer.append("Transaction ID,POS ID,Quantity,Total Price,Timestamp\n");

            // Write transactions
            for (Transaction transaction : transactions) {
                writer.append(transaction.getId().toString())
                        .append(",")
                        .append(transaction.getPosId())
                        .append(",")
                        .append(transaction.getQuantity().toString())
                        .append(",")
                        .append(transaction.getAmount().toString()) // Assuming the total price is in the `amount` field
                        .append(",")
                        .append(transaction.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))) // Format timestamp
                        .append("\n");
            }

            // Send report to sales team
            uploadReportToS3(reportPath);

        } catch (IOException e) {
            System.err.println("Error generating report: " + e.getMessage());
        }
    }

    // Simulate sending the report
    private void sendReportToSalesTeam(String reportPath) {
        System.out.println("Sending sales report to Sales Team: " + reportPath);
        // You could implement actual logic to email the file or upload it to an S3 bucket, etc.
    }


    private void uploadReportToS3(String reportPath) {
        try {
            File reportFile = new File(reportPath);
            String keyName = "reports/sales_report.csv"; // You can customize the key name/path

            // Create a PutObjectRequest to upload the file
            PutObjectRequest request = new PutObjectRequest(s3BucketName, keyName, reportFile);

            // Upload the file to S3
            amazonS3.putObject(request);
            System.out.println("Sales report successfully uploaded to S3: " + keyName);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to upload report to S3: " + e.getMessage());
        }
    }
}
