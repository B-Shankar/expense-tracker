package com.cicd.expense_tracker_api.service;

import com.cicd.expense_tracker_api.dto.ExpenseRequestDto;
import com.cicd.expense_tracker_api.dto.ExpenseResponseDto;
import com.cicd.expense_tracker_api.entity.Expense;
import com.cicd.expense_tracker_api.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseResponseDto createExpense(ExpenseRequestDto dto) {

        log.info("Creating expense with title: {}", dto.getTitle());

        Expense expense = mapToEntity(dto);

        Expense savedExpense = expenseRepository.save(expense);

        log.info("Expense created successfully with id: {}", savedExpense.getId());

        return mapToResponseDto(savedExpense);
    }

    public List<ExpenseResponseDto> getAllExpenses() {

        log.info("Fetching all expenses");

        return expenseRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    public ExpenseResponseDto getExpenseById(Long id) {

        log.info("Fetching expense with id: {}", id);

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Expense not found with id: {}", id);
                    return new RuntimeException("Expense not found");
                });

        return mapToResponseDto(expense);
    }

    public ExpenseResponseDto updateExpense(
            Long id,
            ExpenseRequestDto dto) {

        log.info("Updating expense with id: {}", id);

        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Expense not found with id: {}", id);
                    return new RuntimeException("Expense not found");
                });

        existingExpense.setTitle(dto.getTitle());
        existingExpense.setAmount(dto.getAmount());
        existingExpense.setCategory(dto.getCategory());
        existingExpense.setDescription(dto.getDescription());
        existingExpense.setExpenseDate(dto.getExpenseDate());

        Expense updatedExpense = expenseRepository.save(existingExpense);

        log.info("Expense updated successfully with id: {}", id);

        return mapToResponseDto(updatedExpense);
    }

    public void deleteExpense(Long id) {

        log.info("Deleting expense with id: {}", id);

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Expense not found with id: {}", id);
                    return new RuntimeException("Expense not found");
                });

        expenseRepository.delete(expense);

        log.info("Expense deleted successfully with id: {}", id);
    }

    private Expense mapToEntity(ExpenseRequestDto dto) {

        return Expense.builder()
                .title(dto.getTitle())
                .amount(dto.getAmount())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .expenseDate(dto.getExpenseDate())
                .build();
    }

    private ExpenseResponseDto mapToResponseDto(Expense expense) {

        return ExpenseResponseDto.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .amount(expense.getAmount())
                .category(expense.getCategory())
                .description(expense.getDescription())
                .expenseDate(expense.getExpenseDate())
                .build();
    }
}