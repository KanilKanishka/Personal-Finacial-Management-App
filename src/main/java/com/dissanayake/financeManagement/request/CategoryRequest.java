package com.dissanayake.financeManagement.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class CategoryRequest {
    private Long id;
    private String name;
}
