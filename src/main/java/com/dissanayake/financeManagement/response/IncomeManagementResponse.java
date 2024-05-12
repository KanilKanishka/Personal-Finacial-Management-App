package com.dissanayake.financeManagement.response;


import com.dissanayake.financeManagement.entity.Category;
import lombok.*;

import java.util.Date;

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
