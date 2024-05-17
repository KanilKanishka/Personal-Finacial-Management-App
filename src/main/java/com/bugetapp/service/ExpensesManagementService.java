package com.bugetapp.service;

import com.bugetapp.repository.ExpensesManagementRepository;
import com.bugetapp.entity.Category;
import com.bugetapp.entity.ExpensesManagement;
import com.bugetapp.request.ExpensesManagementRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpensesManagementService {

    private final ExpensesManagementRepository expensesManagementRepository;
    private final CategoryService categoryService;

    public String addExpense(ExpensesManagementRequest expensesManagementRequest){
        Optional<Category> category = categoryService.findCategoryById(expensesManagementRequest.getCategoryId());

        ExpensesManagement expensesManagement = new ExpensesManagement();
        expensesManagement.setCategories(category.get());
        expensesManagement.setDate(expensesManagementRequest.getDate());
        expensesManagement.setDescription(expensesManagementRequest.getDescription());

        try{
            expensesManagementRepository.save(expensesManagement);
            return "Add expenses successfully";
        }catch (Exception e){
            e.printStackTrace();
            return "Add expenses not successfully";
        }
    }

    public Optional<ExpensesManagement> findExpenseById(Long expenseId){
        boolean isPresent = expensesManagementRepository.findById(expenseId).isPresent();
        if (isPresent){
            return expensesManagementRepository.findById(expenseId);
        }
        return Optional.empty();
    }

    public List<ExpensesManagement> findByDateRange(String fromDate, String toDate){
        return expensesManagementRepository.findByDateRange(fromDate, toDate);
    }

    public List<ExpensesManagement> findAllExpense(){
        return expensesManagementRepository.findAll();
    }

    public ExpensesManagement updateExpensesById(Long expenseId,
                                                  ExpensesManagementRequest expensesManagementRequest){

        ExpensesManagement expensesManagement = findExpenseById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        Optional<Category> category = categoryService.findCategoryById(expensesManagementRequest.getCategoryId());

        expensesManagement.setDescription(expensesManagementRequest.getDescription());
        expensesManagement.setDate(expensesManagementRequest.getDate());
        expensesManagement.setCategories(category.get());
        return expensesManagementRepository.save(expensesManagement);
    }

    public void deleteExpenseById(Long expenseId){
        expensesManagementRepository.deleteById(expenseId);
    }
}
