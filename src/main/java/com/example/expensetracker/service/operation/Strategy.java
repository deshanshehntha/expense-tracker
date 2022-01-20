package com.example.expensetracker.service.operation;

public interface Strategy {

    public double doCalculation(double currentBalance, double newEntry);

}
