package com.example.expensetracker.controller;

import com.example.expensetracker.dto.User;
import com.example.expensetracker.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @PostMapping()
    private Long createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    private User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PatchMapping("/validate")
    private User validateUser(@RequestBody User user) {
        return userService.validateUser(user);
    }



}
