package com.example.expensetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Category {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long categoryId;

    @Column(unique = true, nullable = false)
    private String categoryName;

    @Column
    private Double balance;

    @Column
    private Double initialAmount;

    @Column
    private TransactionType type;
}
