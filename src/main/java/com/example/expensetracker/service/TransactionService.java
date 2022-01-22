package com.example.expensetracker.service;

import com.example.expensetracker.dao.CategoryRepository;
import com.example.expensetracker.dao.TransactionRepository;
import com.example.expensetracker.dto.Category;
import com.example.expensetracker.dto.Transaction;
import com.example.expensetracker.dto.TransactionType;
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

        if(TransactionType.INCOME.equals(createdTransaction.getType())) {
            Context context = new Context(new AddToBalance());
            Double incomeBalance = context.executeStrategy(transactionCategory.getBalance(), transaction.getValue());
            transactionCategory.setBalance(incomeBalance);
            categoryRepository.save(transactionCategory);

        } else if(TransactionType.EXPENSE.equals(createdTransaction.getType())) {
            Context contextForRemoveFromSalary = new Context(new SubtractFromBalance());
            Category salaryCategory = getSalaryCategory();
            Double salaryBalance = contextForRemoveFromSalary.executeStrategy(salaryCategory.getBalance(), transaction.getValue());
            salaryCategory.setBalance(salaryBalance);
            categoryRepository.save(salaryCategory);

            Context contextForRemoveFromCategory = new Context(new SubtractFromBalance());
            Double categoryBalance = contextForRemoveFromCategory.executeStrategy(transactionCategory.getBalance(), transaction.getValue());
            transactionCategory.setBalance(categoryBalance);
            categoryRepository.save(transactionCategory);
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

    public Transaction updateTransaction(Long id, Transaction transactionFromRequest) {
        Transaction persistedTransaction = transactionRepository.findById(id).get();
        transactionFromRequest.setTransactionId(persistedTransaction.getTransactionId());
        transactionFromRequest.setTransactionCategoryId(persistedTransaction.getTransactionCategoryId());

        Category transactionCategory = categoryRepository.findCategoryByCategoryName(transactionFromRequest.getCategory());

        if(TransactionType.INCOME.equals(transactionFromRequest.getType())) {
            //remove previous balance
            Context subtractFromCategoryBalance = new Context(new SubtractFromBalance());
            Double revertToOldBalance = subtractFromCategoryBalance.executeStrategy(transactionCategory.getBalance(), persistedTransaction.getValue());
            transactionCategory.setBalance(revertToOldBalance);

            Context addToNewBalance = new Context(new AddToBalance());
            Double newIncomeBalance = addToNewBalance.executeStrategy(transactionCategory.getBalance(), transactionFromRequest.getValue());
            transactionCategory.setBalance(newIncomeBalance);
            categoryRepository.save(transactionCategory);

            return transactionRepository.save(transactionFromRequest);

        } else if (TransactionType.EXPENSE.equals(transactionFromRequest.getType())) {

            Context addToBalanceContext = new Context(new AddToBalance());
            Category salaryCategory = getSalaryCategory();
            Double oldSalaryBalance = addToBalanceContext.executeStrategy(salaryCategory.getBalance(),
                    persistedTransaction.getValue());
            salaryCategory.setBalance(oldSalaryBalance);

            Context subtractNewBalanceContext = new Context(new SubtractFromBalance());
            Double newSalaryBalance = subtractNewBalanceContext.executeStrategy(salaryCategory.getBalance(),
                    transactionFromRequest.getValue());
            salaryCategory.setBalance(newSalaryBalance);
            categoryRepository.save(salaryCategory);

            Context revertToOldCategoryBalance = new Context(new AddToBalance());
            Double oldCategoryBalance = revertToOldCategoryBalance.executeStrategy(transactionCategory.getBalance(),
                    persistedTransaction.getValue());
            transactionCategory.setBalance(oldCategoryBalance);

            Context subtractNewBalanceFromCategory = new Context(new SubtractFromBalance());
            Double newCategoryBalance = subtractNewBalanceFromCategory.executeStrategy(transactionCategory.getBalance(),
                    transactionFromRequest.getValue());
            transactionCategory.setBalance(newCategoryBalance);
            categoryRepository.save(transactionCategory);

            return transactionRepository.save(transactionFromRequest);
        }

        return null;
    }

    public boolean deleteTransaction(Long id) {

        Transaction persistedTransaction = transactionRepository.findById(id).get();

        Category transactionCategory = categoryRepository.findCategoryByCategoryName(persistedTransaction.getCategory());

        if (TransactionType.INCOME.equals(persistedTransaction.getType())) {
            //remove previous balance
            Context subtractFromCategoryBalance = new Context(new SubtractFromBalance());
            Double revertToOldBalance = subtractFromCategoryBalance.executeStrategy(transactionCategory.getBalance(), persistedTransaction.getValue());
            transactionCategory.setBalance(revertToOldBalance);
            categoryRepository.save(transactionCategory);

            try {
                transactionRepository.delete(persistedTransaction);
            } catch (Exception e) {
                return false;
            }
            return true;

        } else if (TransactionType.EXPENSE.equals(persistedTransaction.getType())) {

            Context addToBalanceContext = new Context(new AddToBalance());
            Category salaryCategory = getSalaryCategory();
            Double oldSalaryBalance = addToBalanceContext.executeStrategy(salaryCategory.getBalance(),
                    persistedTransaction.getValue());
            salaryCategory.setBalance(oldSalaryBalance);
            categoryRepository.save(salaryCategory);

            Context revertToOldCategoryBalance = new Context(new AddToBalance());
            Double oldCategoryBalance = revertToOldCategoryBalance.executeStrategy(transactionCategory.getBalance(),
                    persistedTransaction.getValue());
            transactionCategory.setBalance(oldCategoryBalance);
            categoryRepository.save(transactionCategory);

            try {
                transactionRepository.delete(persistedTransaction);
            } catch (Exception e) {
                return false;
            }
            return true;

        }
        return true;
    }

}
