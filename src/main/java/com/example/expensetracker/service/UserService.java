package com.example.expensetracker.service;

import com.example.expensetracker.dao.UserRepository;
import com.example.expensetracker.dto.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public long createUser(User user) {
        User createdUser = userRepository.save(user);
        return createdUser.getUserId();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User validateUser(User userFromRequest) {
        return userRepository
                .findByUserNameAndPassword(userFromRequest.getUserName(), userFromRequest.getPassword());
    }
}
