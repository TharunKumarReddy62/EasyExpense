package com.project.EasyExpense.service.impl;

import com.project.EasyExpense.model.Expense;
import com.project.EasyExpense.model.ExpenseCategory;
import com.project.EasyExpense.model.User;
import com.project.EasyExpense.repository.ExpenseRepository;
import com.project.EasyExpense.repository.UserRepository;
import com.project.EasyExpense.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseServiceImpl(
            ExpenseRepository expenseRepository,
            UserRepository userRepository) {

        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Expense addExpense(Long userId, Expense expense) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        expense.setUser(user);

        return expenseRepository.save(expense);
    }

    @Override
    public void deleteExpense(Long id) {

        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Expense not found"));

        expenseRepository.delete(existingExpense);
    }

    @Override
    public Expense updateExpense(Long id, Expense expense) {

        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Expense not found"));

        existingExpense.setAmount(expense.getAmount());
        existingExpense.setDescription(expense.getDescription());
        existingExpense.setDate(expense.getDate());
        existingExpense.setCategory(expense.getCategory());

        return expenseRepository.save(existingExpense);
    }

    @Override
    public Expense getExpenseById(Long id) {

        return expenseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Expense not found"));
    }

    @Override
    public List<Expense> getExpensesByCategory(
            Long userId,
            ExpenseCategory category) {

        return expenseRepository.findByUserIdAndCategory(
                userId,
                category
        );
    }

    @Override
    public List<Expense> getExpensesBetweenDates(
            Long userId,
            LocalDate startDate,
            LocalDate endDate) {

        return expenseRepository.findByUserIdAndDateBetween(
                userId,
                startDate,
                endDate
        );
    }

    @Override
    public List<Expense> getExpensesByCategoryBetweenDates(
            Long userId,
            ExpenseCategory category,
            LocalDate startDate,
            LocalDate endDate) {

        return expenseRepository
                .findByUserIdAndCategoryAndDateBetween(
                        userId,
                        category,
                        startDate,
                        endDate
                );
    }
}