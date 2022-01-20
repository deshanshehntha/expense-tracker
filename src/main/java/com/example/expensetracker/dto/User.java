package com.example.expensetracker.dto;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;

    @Column
    private String name;

    @Column
    private String userName;

    @Column
    private String password;

}
