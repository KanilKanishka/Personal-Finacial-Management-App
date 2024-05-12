package com.dissanayake.financeManagement.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;


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
    @UpdateTimestamp
    private String date;
    private Double amount;
    @OneToOne(cascade = CascadeType.ALL)
    private Category categories;
    private String note;
    private String description;
}
