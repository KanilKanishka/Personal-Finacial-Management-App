package com.budgetapp.response;


import com.budgetapp.entity.Category;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class IncomeManagementResponse {
    private Long id;
    private String date;
    private Double amount;
    private Category category;
    private String note;
    private String description;
}
