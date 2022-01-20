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

    @Column
    private String categoryName;

    @Column
    private Double balance;
}
