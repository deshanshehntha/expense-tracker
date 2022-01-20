package com.example.expensetracker.service.operation;

public class SubtractFromBalance implements Strategy{
    @Override
    public double doCalculation(double currentBalance, double newEntry) {
        return currentBalance - newEntry;
    }
}
