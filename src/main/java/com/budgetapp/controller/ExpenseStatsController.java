package com.budgetapp.controller;

import com.budgetapp.response.ExpenseStatsResponse;
import com.budgetapp.service.ExpensesManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/expense/stats/")
@RequiredArgsConstructor
public class ExpenseStatsController {

    private final ExpensesManagementService expensesManagementService;

    @GetMapping("/")
    public Map<String, Object> expenseStatsFindByDateRange(@RequestParam("fromDate") String fromDate,
                                                           @RequestParam("toDate") String toDate){

        Map<String, Object> response = new HashMap<>();

        if (!expensesManagementService.expenseStatsFindByDateRange(fromDate, toDate).isEmpty()){
            List<ExpenseStatsResponse> expensesManagementList = expensesManagementService.expenseStatsFindByDateRange(fromDate, toDate);

            response.put("TOTAL_EXPENSES_AMOUNT", expensesManagementService.totalExpensesByDateRange(fromDate, toDate));
            response.put("STATUS", "SUCCESS");
            response.put("DATA", expensesManagementList);
        }else {
            response.put("STATUS", "SUCCESS");
            response.put("DATA", "NOT_FOUND_DATA");
        }
        return response;
    }
}
