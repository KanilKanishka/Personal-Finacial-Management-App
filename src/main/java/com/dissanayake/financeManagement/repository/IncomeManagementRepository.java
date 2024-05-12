package com.dissanayake.financeManagement.repository;

import com.dissanayake.financeManagement.entity.IncomeManagement;
import com.dissanayake.financeManagement.response.IncomeManagementResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IncomeManagementRepository extends JpaRepository<IncomeManagement, Long> {
    Optional<IncomeManagement> findById(Long id);

    @Query("SELECT incomeManagement FROM IncomeManagement incomeManagement WHERE incomeManagement.date >= :fromDate AND incomeManagement.date <= :toDate")
    List<IncomeManagement> findByDateRange(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
}
