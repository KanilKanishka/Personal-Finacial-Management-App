package com.budgetapp.response;


import com.budgetapp.entity.Category;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ExpenseStatsResponse {
    private Double amount;
    private Category categories;
    private Double statsPercentage;
    private String date;
}
