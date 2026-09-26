package com.project.EasyExpense.service;

import com.project.EasyExpense.model.Expense;
import com.project.EasyExpense.model.ExpenseCategory;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {

    Expense addExpense(Long userId, Expense expense);

    void deleteExpense(Long id);

    Expense updateExpense(Long id, Expense expense);

    Expense getExpenseById(Long id);

    List<Expense> getExpensesByCategory(
            Long userId,
            ExpenseCategory category
    );

    List<Expense> getExpensesBetweenDates(
            Long userId,
            LocalDate startDate,
            LocalDate endDate
    );

    List<Expense> getExpensesByCategoryBetweenDates(
            Long userId,
            ExpenseCategory category,
            LocalDate startDate,
            LocalDate endDate
    );
}