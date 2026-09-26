package com.project.EasyExpense.repository;

import com.project.EasyExpense.model.Expense;
import com.project.EasyExpense.model.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserIdAndCategory(
            Long userId,
            ExpenseCategory category
    );

    List<Expense> findByUserIdAndDateBetween(
            Long userId,
            LocalDate startDate,
            LocalDate endDate
    );

    List<Expense> findByUserIdAndCategoryAndDateBetween(
            Long userId,
            ExpenseCategory category,
            LocalDate startDate,
            LocalDate endDate
    );
}