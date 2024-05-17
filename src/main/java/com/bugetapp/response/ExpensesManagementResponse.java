package com.bugetapp.response;


import com.bugetapp.entity.Category;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ExpensesManagementResponse {
    private Long id;
    private String description;
    private String date;
    private Category categories;
}
