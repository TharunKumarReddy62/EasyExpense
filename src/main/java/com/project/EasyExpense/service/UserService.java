package com.project.EasyExpense.service;

import com.project.EasyExpense.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);


    User getUserById(Long id);
}