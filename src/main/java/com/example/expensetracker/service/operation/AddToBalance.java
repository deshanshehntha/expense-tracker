package com.example.expensetracker.service.operation;

public class AddToBalance implements Strategy {
    @Override
    public double doCalculation(double currentBalance, double newEntry) {
        return currentBalance + newEntry;
    }
}
