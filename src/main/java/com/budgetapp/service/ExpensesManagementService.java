package com.budgetapp.service;

import com.budgetapp.entity.Category;
import com.budgetapp.entity.ExpensesManagement;
import com.budgetapp.exception.ResourceNotFoundException;
import com.budgetapp.repository.ExpensesManagementRepository;
import com.budgetapp.request.ExpensesManagementRequest;
import com.budgetapp.response.ExpenseStatsResponse;
import com.budgetapp.response.ExpensesManagementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpensesManagementService {

    private final ExpensesManagementRepository expensesManagementRepository;
    private final CategoryService categoryService;

    private Double totalIncome;

    private void initializeTotalIncome() {
        this.totalIncome = Optional.ofNullable(getTotalExpense()).orElse(0.0);
    }

    public void addExpense(ExpensesManagementRequest expensesManagementRequest) {
        Category category = categoryService.findCategoryById(expensesManagementRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + expensesManagementRequest.getCategoryId()));

        ExpensesManagement expensesManagement = new ExpensesManagement();
        expensesManagement.setAmount(expensesManagementRequest.getAmount());
        expensesManagement.setCategory(category);
        expensesManagement.setDate(expensesManagementRequest.getDate());
        expensesManagement.setNote(expensesManagementRequest.getNote());
        expensesManagement.setDescription(expensesManagementRequest.getDescription());

        try {
            expensesManagementRepository.save(expensesManagement);
            initializeTotalIncome();
            updateStatsPercentage();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding expense: " + e.getMessage());
        }
    }

    private void updateStatsPercentage() {
        List<ExpensesManagement> allExpensesManagement = expensesManagementRepository.findAll();
        for (ExpensesManagement oneExpensesManagement : allExpensesManagement) {
            final Double statsPercentage = (oneExpensesManagement.getAmount() / this.totalIncome) * 100;
            oneExpensesManagement.setStatsPercentage(statsPercentage);
            expensesManagementRepository.save(oneExpensesManagement);
        }
    }

    public ExpensesManagementResponse findExpenseById(Long expenseId) {
        ExpensesManagement expensesManagement = expensesManagementRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found for this id :: " + expenseId));
        return mapToResponse(expensesManagement);
    }

    public List<ExpensesManagementResponse> findByDateRange(String fromDate, String toDate) {
        List<ExpensesManagement> expensesManagementList = expensesManagementRepository.findByDateRange(fromDate, toDate);
        return expensesManagementList.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<ExpenseStatsResponse> expenseStatsFindByDateRange(String fromDate, String toDate) {
        List<ExpensesManagement> expensesManagementList = expensesManagementRepository.findByDateRange(fromDate, toDate);
        return expensesManagementList.stream().map(this::mapToExpenseStatsResponse).collect(Collectors.toList());
    }

    public List<ExpensesManagementResponse> findAllExpense() {
        List<ExpensesManagement> expensesManagementList = expensesManagementRepository.findAll();
        return expensesManagementList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Double totalExpensesByDateRange(String fromDate, String toDate){
        return expensesManagementRepository.totalExpensesByDateRange(fromDate, toDate);
    }

    public ExpensesManagementResponse updateExpensesById(Long expenseId, ExpensesManagementRequest expensesManagementRequest) {
        ExpensesManagement expensesManagement = expensesManagementRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found for this id :: " + expenseId));

        Category category = categoryService.findCategoryById(expensesManagementRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + expensesManagementRequest.getCategoryId()));

        expensesManagement.setDescription(expensesManagementRequest.getDescription());
        expensesManagement.setDate(expensesManagementRequest.getDate());
        expensesManagement.setCategory(category);
        expensesManagement.setAmount(expensesManagementRequest.getAmount());

        ExpensesManagement updatedExpense = expensesManagementRepository.save(expensesManagement);
        return mapToResponse(updatedExpense);
    }

    public void deleteExpenseById(Long expenseId) {
        ExpensesManagementResponse expensesManagementResponse = findExpenseById(expenseId);
        expensesManagementRepository.deleteById(expenseId);
    }

    public Double getTotalExpense() {
        return expensesManagementRepository.getTotalExpense();
    }

    private ExpensesManagementResponse mapToResponse(ExpensesManagement expensesManagement) {
        return new ExpensesManagementResponse(
                expensesManagement.getDescription(),
                expensesManagement.getAmount(),
                expensesManagement.getCategory(),
                expensesManagement.getNote(),
                expensesManagement.getDate()
        );
    }

    private ExpenseStatsResponse mapToExpenseStatsResponse(ExpensesManagement expensesManagement) {
        return new ExpenseStatsResponse(
                expensesManagement.getAmount(),
                expensesManagement.getCategory(),
                expensesManagement.getStatsPercentage(),
                expensesManagement.getDate()
        );
    }
}
