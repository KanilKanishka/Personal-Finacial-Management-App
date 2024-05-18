package com.budgetapp.response;


import com.budgetapp.entity.Category;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ExpensesManagementResponse {
    private String description;
    private Double amount;
    private Category category;
    private String note;
    private String date;
}
