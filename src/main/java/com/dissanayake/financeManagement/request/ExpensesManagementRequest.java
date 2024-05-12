package com.dissanayake.financeManagement.request;


import com.dissanayake.financeManagement.entity.Category;
import lombok.*;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ExpensesManagementRequest {
    private String description;
    private String date;
    private Long categoryId;
}
