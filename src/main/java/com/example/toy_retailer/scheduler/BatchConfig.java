package com.example.toy_retailer.scheduler;

import com.example.toy_retailer.entity.Transaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;

import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public JdbcCursorItemReader<Transaction> reader(DataSource dataSource) {
        JdbcCursorItemReader<Transaction> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT * FROM transaction");
        reader.setRowMapper((resultSet, rowNum) -> {
            Transaction transaction = new Transaction();
            transaction.setId(resultSet.getLong("id"));
            transaction.setPosId(resultSet.getString("pos_id"));
            transaction.setAmount(resultSet.getBigDecimal("amount"));
            transaction.setQuantity(resultSet.getInt("quantity"));
            transaction.setTimestamp(resultSet.getTimestamp("timestamp").toLocalDateTime());
            return transaction;
        });
        return reader;
    }

    @Bean
    public JdbcBatchItemWriter<Transaction> writer(DataSource dataSource) {
        JdbcBatchItemWriter<Transaction> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("INSERT INTO transactions_output (id, pos_id, amount, quantity, timestamp) VALUES (:id, :posId, :amount, :quantity, :timestamp)");  // Insert into the output table in PostgreSQL
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, JdbcCursorItemReader<Transaction> reader, JdbcBatchItemWriter<Transaction> writer) {
        return stepBuilderFactory.get("step1")
                .<Transaction, Transaction>chunk(10)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, Step step1) {
        return jobBuilderFactory.get("transactionJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }
}
