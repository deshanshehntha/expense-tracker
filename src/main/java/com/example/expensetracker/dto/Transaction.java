package com.example.expensetracker.dto;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Transaction {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long transactionId;

    @Column
    private Long transactionCategoryId;

    private String category;

    @Column
    private double value;

    @Column
    private String note;
}
