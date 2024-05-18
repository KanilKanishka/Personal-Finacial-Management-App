package com.budgetapp.repository;

import com.budgetapp.entity.ExpensesManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ExpensesManagementRepository extends JpaRepository<ExpensesManagement, Long> {
    Optional<ExpensesManagement> findById(Long id);

    @Query("SELECT expensesManagement FROM ExpensesManagement expensesManagement WHERE expensesManagement.date >= :fromDate AND expensesManagement.date <= :toDate")
    List<ExpensesManagement> findByDateRange(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

    @Query("SELECT SUM(expensesManagement.amount) FROM ExpensesManagement expensesManagement")
    Double getTotalExpense();

    @Query("SELECT SUM(expensesManagement.amount) FROM ExpensesManagement expensesManagement WHERE expensesManagement.date >= :fromDate AND expensesManagement.date <= :toDate")
    Double totalExpensesByDateRange(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
}
