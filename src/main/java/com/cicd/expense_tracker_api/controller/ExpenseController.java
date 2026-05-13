package com.cicd.expense_tracker_api.controller;

import com.cicd.expense_tracker_api.dto.ExpenseRequestDto;
import com.cicd.expense_tracker_api.dto.ExpenseResponseDto;
import com.cicd.expense_tracker_api.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ExpenseResponseDto createExpense(@Valid @RequestBody ExpenseRequestDto expense) {
        return expenseService.createExpense(expense);
    }

    @GetMapping
    public List<ExpenseResponseDto> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/{id}")
    public ExpenseResponseDto getExpenseById(@PathVariable Long id) {
        return expenseService.getExpenseById(id);
    }

    @PutMapping("/{id}")
    public ExpenseResponseDto updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequestDto expense) {

        return expenseService.updateExpense(id, expense);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id) {

        expenseService.deleteExpense(id);

        return "Expense deleted successfully";
    }

    @GetMapping("/test")
    public String testingApi() {
        return "Application is running!";
    }
}
