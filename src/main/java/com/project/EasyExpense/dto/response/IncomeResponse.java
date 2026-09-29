package com.project.EasyExpense.dto.response;

import com.project.EasyExpense.model.IncomeSource;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomeResponse {

    private Long id;

    private BigDecimal amount;

    private String description;

    private LocalDate date;

    private IncomeSource source;

    private Long userId;
}