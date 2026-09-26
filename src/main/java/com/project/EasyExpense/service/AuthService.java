package com.project.EasyExpense.service;

import com.project.EasyExpense.model.User;

public interface AuthService {

    User signup(User user);

    User login(String email, String password);
}