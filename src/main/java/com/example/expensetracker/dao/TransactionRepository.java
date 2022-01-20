package com.example.expensetracker.dao;

import com.example.expensetracker.dto.Category;
import com.example.expensetracker.dto.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {}
