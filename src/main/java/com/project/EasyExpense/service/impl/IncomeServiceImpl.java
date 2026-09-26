package com.project.EasyExpense.service.impl;

import com.project.EasyExpense.model.Income;
import com.project.EasyExpense.model.IncomeSource;
import com.project.EasyExpense.model.User;
import com.project.EasyExpense.repository.IncomeRepository;
import com.project.EasyExpense.repository.UserRepository;
import com.project.EasyExpense.service.IncomeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final UserRepository userRepository;

    public IncomeServiceImpl(
            IncomeRepository incomeRepository,
            UserRepository userRepository) {

        this.incomeRepository = incomeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Income addIncome(Long userId, Income income) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        income.setUser(user);

        return incomeRepository.save(income);
    }

    @Override
    public void deleteIncome(Long id) {

        Income existingIncome = incomeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Income not found"));

        incomeRepository.delete(existingIncome);
    }

    @Override
    public Income updateIncome(Long id, Income income) {

        Income existingIncome = incomeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Income not found"));

        existingIncome.setAmount(income.getAmount());
        existingIncome.setDescription(income.getDescription());
        existingIncome.setDate(income.getDate());
        existingIncome.setSource(income.getSource());

        return incomeRepository.save(existingIncome);
    }

    @Override
    public Income getIncomeById(Long id) {

        return incomeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Income not found"));
    }

    @Override
    public List<Income> getIncomesBetweenDates(
            Long userId,
            LocalDate startDate,
            LocalDate endDate) {

        return incomeRepository.findByUserIdAndDateBetween(
                userId,
                startDate,
                endDate
        );
    }

    @Override
    public List<Income> getIncomeBySource(
            Long userId,
            IncomeSource source) {

        return incomeRepository.findByUserIdAndSource(
                userId,
                source
        );
    }
}