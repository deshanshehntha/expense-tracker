package com.example.expensetracker.service.operation;

public class Context {

    private Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public double executeStrategy(double value1, double value2){
        return strategy.doCalculation(value1, value2);
    }
}