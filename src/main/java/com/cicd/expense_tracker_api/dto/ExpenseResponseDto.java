package com.cicd.expense_tracker_api.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseResponseDto {

    private Long id;

    private String title;

    private BigDecimal amount;

    private String category;

    private String description;

    private LocalDate expenseDate;
}