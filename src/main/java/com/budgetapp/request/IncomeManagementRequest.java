package com.budgetapp.request;

import lombok.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class IncomeManagementRequest {
    private String date;
    private Double amount;
    private Long categoryId;
    private String note;
    private String description;
}
