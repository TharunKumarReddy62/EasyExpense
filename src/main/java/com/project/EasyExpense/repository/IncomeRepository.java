package com.project.EasyExpense.repository;

import com.project.EasyExpense.model.Income;
import com.project.EasyExpense.model.IncomeSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findByUserIdAndDateBetween(
            Long userId,
            LocalDate startDate,
            LocalDate endDate
    );

    List<Income> findByUserIdAndSource(
            Long userId,
            IncomeSource source
    );
}