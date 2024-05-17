package com.bugetapp.request;


import lombok.*;


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
