package com.budgetapp.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "income_management")
public class IncomeManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_id")
    private Long id;
    private String date;
    private Double amount;
    @ManyToOne
    @JoinColumn(unique = false)
    private Category category;
    private String note;
    private String description;
    private Double statsPercentage;
}
