package com.budgetapp.entity;



import lombok.*;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expenses_management")
public class ExpensesManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "e")
    private Long id;
    private String description;
    private String date;
    @OneToOne(cascade = CascadeType.ALL)
    private Category categories;
}
