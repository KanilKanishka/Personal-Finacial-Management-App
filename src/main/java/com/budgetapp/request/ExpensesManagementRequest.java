package com.budgetapp.request;


import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ExpensesManagementRequest {
    private Double amount;
    private String date;
    private Long categoryId;
    private String description;
    private String note;
    private Double statsPercentage;
}
