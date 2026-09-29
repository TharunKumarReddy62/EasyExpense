package com.project.EasyExpense.dto.response;

import com.project.EasyExpense.model.ExpenseCategory;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseResponse {

    private Long id;

    private BigDecimal amount;

    private String description;

    private LocalDate date;

    private ExpenseCategory category;

    private Long userId;
}