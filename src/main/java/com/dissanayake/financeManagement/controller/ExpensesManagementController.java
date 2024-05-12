package com.dissanayake.financeManagement.controller;


import com.dissanayake.financeManagement.entity.ExpensesManagement;
import com.dissanayake.financeManagement.request.ExpensesManagementRequest;
import com.dissanayake.financeManagement.service.ExpensesManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/expense/management")
public class ExpensesManagementController {

    private final ExpensesManagementService expensesManagementService;

    @PostMapping("/add")
    public String addExpense(@RequestBody ExpensesManagementRequest expensesManagementRequest) {
        return expensesManagementService.addExpense(expensesManagementRequest);
    }

    @GetMapping("/findById")
    public Optional<ExpensesManagement> findExpenseById(@RequestParam("expenseId") Long expenseId){
        return expensesManagementService.findExpenseById(expenseId);
    }

    @GetMapping("/findByDateRange")
    public List<ExpensesManagement> findByDateRange(@RequestParam("fromDate") String fromDate,
                                                        @RequestParam("toDate") String toDate){
        return expensesManagementService.findByDateRange(fromDate, toDate);
    }

    @GetMapping("/findAll")
    public List<ExpensesManagement> findAllExpense(){
        return expensesManagementService.findAllExpense();
    }

    @PutMapping("/update")
    public ExpensesManagement updateExpenseById(
            @RequestParam("expenseId") Long expenseId,
            @RequestBody ExpensesManagementRequest expensesManagementRequest){
        return expensesManagementService.updateExpensesById(expenseId, expensesManagementRequest);
    }

    @DeleteMapping("/delete")
    public String deleteExpenseById(@RequestParam("expenseId") Long expenseId){
        expensesManagementService.deleteExpenseById(expenseId);
        return "Delete successfully";
    }
}
