package com.bugetapp.request;

import com.bugetapp.entity.Category;
import lombok.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class IncomeManagementRequest {
    private String date;
    private Double amount;
    private Category category;
    private String note;
    private String description;
}
