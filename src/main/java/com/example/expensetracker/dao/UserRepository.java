package com.example.expensetracker.dao;

import com.example.expensetracker.dto.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUserNameAndPassword(String name, String password);
}
