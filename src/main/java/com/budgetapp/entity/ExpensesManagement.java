package com.budgetapp.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expenses_management")
public class ExpensesManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Double amount;
    private String note;
    private String date;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = false)
    private Category category;
    private Double statsPercentage;
}
