package com.budgetapp.response;

import com.budgetapp.entity.Category;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class IncomeStatsResponse {
    private Double amount;
    private Category category;
    private Double statsPercentage;
    private String data;
}
