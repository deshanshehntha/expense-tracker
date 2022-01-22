package com.example.expensetracker.controller;

import com.example.expensetracker.dto.Category;
import com.example.expensetracker.dto.Transaction;
import com.example.expensetracker.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/transaction")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping()
    private Long createTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction);
    }

    @PatchMapping("/{id}")
    private Transaction updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        return transactionService.updateTransaction(id, transaction);
    }

    @DeleteMapping("/{id}")
    private boolean deleteTransaction(@PathVariable Long id) {
        return transactionService.deleteTransaction(id);
    }

    @GetMapping()
    private List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }
}
