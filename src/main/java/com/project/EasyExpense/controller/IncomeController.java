package com.project.EasyExpense.controller;

import com.project.EasyExpense.dto.request.IncomeRequest;
import com.project.EasyExpense.dto.response.IncomeResponse;
import com.project.EasyExpense.model.Income;
import com.project.EasyExpense.model.IncomeSource;
import com.project.EasyExpense.service.IncomeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<IncomeResponse> addIncome(
            @PathVariable Long userId,
            @Valid @RequestBody IncomeRequest request) {

        Income income = Income.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .date(request.getDate())
                .source(request.getSource())
                .build();

        Income savedIncome =
                incomeService.addIncome(userId, income);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(savedIncome));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncomeResponse> getIncomeById(
            @PathVariable Long id) {

        Income income = incomeService.getIncomeById(id);

        return ResponseEntity.ok(toResponse(income));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncomeResponse> updateIncome(
            @PathVariable Long id,
            @Valid @RequestBody IncomeRequest request) {

        Income income = Income.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .date(request.getDate())
                .source(request.getSource())
                .build();

        Income updatedIncome =
                incomeService.updateIncome(id, income);

        return ResponseEntity.ok(toResponse(updatedIncome));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(
            @PathVariable Long id) {

        incomeService.deleteIncome(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<IncomeResponse>> getIncomesBetweenDates(
            @PathVariable Long userId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        List<IncomeResponse> incomes =
                incomeService
                        .getIncomesBetweenDates(
                                userId,
                                startDate,
                                endDate
                        )
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return ResponseEntity.ok(incomes);
    }

    @GetMapping("/user/{userId}/source/{source}")
    public ResponseEntity<List<IncomeResponse>> getIncomeBySource(
            @PathVariable Long userId,
            @PathVariable IncomeSource source) {

        List<IncomeResponse> incomes =
                incomeService
                        .getIncomeBySource(userId, source)
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return ResponseEntity.ok(incomes);
    }

    private IncomeResponse toResponse(Income income) {

        return IncomeResponse.builder()
                .id(income.getId())
                .amount(income.getAmount())
                .description(income.getDescription())
                .date(income.getDate())
                .source(income.getSource())
                .userId(income.getUser().getId())
                .build();
    }
}