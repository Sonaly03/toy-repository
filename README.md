
Project Overview:
A major toy retailer is developing a cloud-based solution for managing Point of Sale (POS) transactions and back-office inventory.

Implemented a solution in Java, SpringBoot and AWS

The solution entails that the transaction entered in the database is valid using the spring annotations
and also utilized spring batch capabilities to enable batching process and transmitting data to different entities
Implemented security config as well to handle unauthenticated access.
Created a Transaction class to handle the various attributes of transactions

Implemented 4 HTTP endpoints:
    GET(http://localhost:8080/api/transactions): to get all the transactions
    POST(http://localhost:8080/api/transactions/recieve-transactions): to send the transactions to be saved in the PostgresSQL db
    POST(http://localhost:8080/api/transactions/send-to-back-office): to send transaction data to backoffice
    POST(http://localhost:8080/api/transactions/generate-report): to generate sales report and store in S3
    POST(http://localhost:8080/api/transactions/send-to-sales-team): to send to sales office manually triggering batch process

Connected to the AWS cloud for storing the database by using teh RDS
Connected to the S3 for uploading the reports and viewing them for the sales team
Tested using postman by hitting the differnent endpoints and seeing the data saved inthe db and the reports generated on s3.


![Screenshot 2025-03-17 112252](https://github.com/user-attachments/assets/8aabdb48-ad17-4cb5-a23a-c80206fd04cb)

![Screenshot 2025-03-17 113949](https://github.com/user-attachments/assets/ebe26e30-4c8b-40a9-b195-21be9eb838ae)


![Screenshot 2025-03-17 114121](https://github.com/user-attachments/assets/fa088765-fd2b-475e-a4d5-542603a3441e)


![Screenshot 2025-03-17 114220](https://github.com/user-attachments/assets/35edf94b-e026-4e0d-a75e-47a40810c730)


