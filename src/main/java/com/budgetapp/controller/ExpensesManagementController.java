package com.budgetapp.controller;


import com.budgetapp.request.ExpensesManagementRequest;
import com.budgetapp.service.ExpensesManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/expense/management")
public class ExpensesManagementController {

    private final ExpensesManagementService expensesManagementService;

    @PostMapping("/add")
    public Map<String, Object> addExpense(@RequestBody ExpensesManagementRequest expensesManagementRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            expensesManagementService.addExpense(expensesManagementRequest);
            response.put("STATUS", "SUCCESS");
            response.put("DATA", "ADD_EXPENSE_SUCCESS");
            return response;
        }catch (Exception e){
            e.printStackTrace();
            response.put("STATUS", "FAILED");
            response.put("DATA", "ADD_EXPENSE_FAILED");
            return response;
        }
    }

    @GetMapping("/findById")
    public Map<String, Object> findExpenseById(@RequestParam("expenseId") Long expenseId){

        Map<String, Object> response = new HashMap<>();

        try{
            expensesManagementService.findExpenseById(expenseId);
            response.put("STATUS", "SUCCESS");
            response.put("DATA", expensesManagementService.findExpenseById(expenseId));
            return response;
        }catch (Exception e){
            response.put("STATUS", "SUCCESS");
            response.put("DATA", "NOT_FOUND_DATA");
            return response;
        }
    }

    @GetMapping("/findByDateRange")
    public Map<String, Object> findByDateRange(@RequestParam("fromDate") String fromDate,
                                                        @RequestParam("toDate") String toDate){

        Map<String, Object> response = new HashMap<>();

        if (!expensesManagementService.findByDateRange(fromDate, toDate).isEmpty()){
            response.put("STATUS", "SUCCESS");
            response.put("DATA", expensesManagementService.findByDateRange(fromDate, toDate));
            response.put("TOTAL_EXPENSES_AMOUNT", expensesManagementService.totalExpensesByDateRange(fromDate, toDate));
            return response;
        }else {
            response.put("STATUS", "SUCCESS");
            response.put("DATA", "NOT_FOUND_DATA");
            return response;
        }
    }

    @GetMapping("/findAll")
    public Map<String, Object> findAllExpense(){
        Map<String, Object> response = new HashMap<>();

        if (!expensesManagementService.findAllExpense().isEmpty()){
            response.put("STATUS", "SUCCESS");
            response.put("DATA", expensesManagementService.findAllExpense());
            response.put("TOTAL_EXPENSES_AMOUNT", expensesManagementService.getTotalExpense());
            return response;
        }else {
            response.put("STATUS", "SUCCESS");
            response.put("DATA", "NOT_FOUND_DATA");
            return response;
        }
    }

    @PutMapping("/update")
    public Map<String, Object> updateExpenseById(
            @RequestParam("expenseId") Long expenseId,
            @RequestBody ExpensesManagementRequest expensesManagementRequest){

        Map<String, Object> response = new HashMap<>();

        try {
            expensesManagementService.updateExpensesById(expenseId, expensesManagementRequest);
            response.put("STATUS", "SUCCESS");
            response.put("DATA", expensesManagementService.updateExpensesById(expenseId, expensesManagementRequest));
            return response;
        }catch (Exception e){
            e.printStackTrace();
            response.put("STATUS", "FAILED");
            response.put("DATA", "UPDATE_FAILED");
            return response;
        }
    }

    @DeleteMapping("/delete")
    public Map<String, Object> deleteExpenseById(@RequestParam("expenseId") Long expenseId){
        Map<String, Object> response = new HashMap<>();

        try{
            expensesManagementService.deleteExpenseById(expenseId);
            response.put("STATUS", "SUCCESS");
            response.put("DATA", "DELETE_SUCCESS");
            return response;
        }catch (Exception e){
            e.printStackTrace();
            response.put("STATUS", "FAILED");
            response.put("DATA", "DELETE_FAILED");
            return response;
        }
    }
}