package com.project.EasyExpense.controller;

import com.project.EasyExpense.dto.request.ExpenseRequest;
import com.project.EasyExpense.dto.response.ExpenseResponse;
import com.project.EasyExpense.model.Expense;
import com.project.EasyExpense.model.ExpenseCategory;
import com.project.EasyExpense.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ExpenseResponse> addExpense(
            @PathVariable Long userId,
            @Valid @RequestBody ExpenseRequest request) {

        Expense expense = Expense.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .date(request.getDate())
                .category(request.getCategory())
                .build();

        Expense savedExpense =
                expenseService.addExpense(userId, expense);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(savedExpense));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getExpenseById(
            @PathVariable Long id) {

        Expense expense = expenseService.getExpenseById(id);

        return ResponseEntity.ok(toResponse(expense));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequest request) {

        Expense expense = Expense.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .date(request.getDate())
                .category(request.getCategory())
                .build();

        Expense updatedExpense =
                expenseService.updateExpense(id, expense);

        return ResponseEntity.ok(toResponse(updatedExpense));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(
            @PathVariable Long id) {

        expenseService.deleteExpense(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}/category/{category}")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByCategory(
            @PathVariable Long userId,
            @PathVariable ExpenseCategory category) {

        List<ExpenseResponse> expenses =
                expenseService
                        .getExpensesByCategory(userId, category)
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<ExpenseResponse>> getExpensesBetweenDates(
            @PathVariable Long userId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        List<ExpenseResponse> expenses =
                expenseService
                        .getExpensesBetweenDates(
                                userId,
                                startDate,
                                endDate
                        )
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/user/{userId}/category/{category}/date-range")
    public ResponseEntity<List<ExpenseResponse>>
    getExpensesByCategoryBetweenDates(
            @PathVariable Long userId,
            @PathVariable ExpenseCategory category,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        List<ExpenseResponse> expenses =
                expenseService
                        .getExpensesByCategoryBetweenDates(
                                userId,
                                category,
                                startDate,
                                endDate
                        )
                        .stream()
                        .map(this::toResponse)
                        .toList();

        return ResponseEntity.ok(expenses);
    }

    private ExpenseResponse toResponse(Expense expense) {

        return ExpenseResponse.builder()
                .id(expense.getId())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .date(expense.getDate())
                .category(expense.getCategory())
                .userId(expense.getUser().getId())
                .build();
    }
}