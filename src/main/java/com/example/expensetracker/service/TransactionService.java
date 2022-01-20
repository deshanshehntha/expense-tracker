package com.example.expensetracker.service;

import com.example.expensetracker.dao.CategoryRepository;
import com.example.expensetracker.dao.TransactionRepository;
import com.example.expensetracker.dto.Category;
import com.example.expensetracker.dto.Transaction;
import com.example.expensetracker.service.operation.SubtractFromBalance;
import com.example.expensetracker.service.operation.Context;
import com.example.expensetracker.service.operation.AddToBalance;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private static final String SALARY_CATEGORY = "SALARY";

    public Long createTransaction(Transaction transaction) {

        Category transactionCategory = categoryRepository.findCategoryByCategoryName(transaction.getCategory());
        transaction.setTransactionCategoryId(transactionCategory.getCategoryId());

        Transaction createdTransaction = transactionRepository.save(transaction);

        Double newBalance = null;
        if("SALARY".equals(transactionCategory.getCategoryName())) {
            Context context = new Context(new AddToBalance());
            newBalance = context.executeStrategy(transactionCategory.getBalance(), transaction.getValue());
            transactionCategory.setBalance(newBalance);

        } else {
            Context context = new Context(new AddToBalance());
            newBalance = context.executeStrategy(transactionCategory.getBalance(), transaction.getValue());
            transactionCategory.setBalance(newBalance);
            categoryRepository.save(transactionCategory);

            Context subtractContext = new Context(new SubtractFromBalance());
            Category salaryCategory = getSalaryCategory();
            Double updatedSalary = subtractContext.executeStrategy(salaryCategory.getBalance(), transaction.getValue());
            salaryCategory.setBalance(updatedSalary);
            categoryRepository.save(salaryCategory);
        }

        return createdTransaction.getTransactionId();
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = (List<Transaction>) transactionRepository.findAll();
        transactions.forEach(transaction -> {
            Category relatedCategory = categoryRepository.findCategoryByCategoryId(transaction.getTransactionCategoryId());
            transaction.setCategory(relatedCategory.getCategoryName());
        });
        return transactions;
    }

    public Category getSalaryCategory() {
        return categoryRepository.findCategoryByCategoryName(SALARY_CATEGORY);
    }

}
