package com.project.EasyExpense.service;

import com.project.EasyExpense.model.Income;
import com.project.EasyExpense.model.IncomeSource;

import java.time.LocalDate;
import java.util.List;

public interface IncomeService {

    Income addIncome(Long userId, Income income);

    void deleteIncome(Long id);

    Income updateIncome(Long id, Income income);

    Income getIncomeById(Long id);

    List<Income> getIncomesBetweenDates(
            Long userId,
            LocalDate startDate,
            LocalDate endDate
    );

    List<Income> getIncomeBySource(
            Long userId,
            IncomeSource source
    );
}