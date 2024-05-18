package com.budgetapp.controller;

import com.budgetapp.response.IncomeStatsResponse;
import com.budgetapp.service.IncomeManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/income/stats")
@RequiredArgsConstructor
public class IncomeStatsController {

    private final IncomeManagementService incomeManagementService;


    @GetMapping("/")
    public Map<String, Object> incomeStatsFindByDateRange(@RequestParam("fromDate") String fromDate,
                                                          @RequestParam("toDate") String toDate){

        Map<String, Object> response = new HashMap<>();

        if (!incomeManagementService.incomeStatsFindByDateRange(fromDate, toDate).isEmpty()){
            List<IncomeStatsResponse> incomeManagementList = incomeManagementService.incomeStatsFindByDateRange(fromDate, toDate);

            response.put("STATUS", "SUCCESS");
            response.put("DATA", incomeManagementList);
            response.put("TOTAL_INCOME_AMOUNT", incomeManagementService.totalIncomeByDateRange(fromDate, toDate));
        }else {
            response.put("STATUS", "SUCCESS");
            response.put("DATA", "NOT_FOUND_DATA");
        }
        return response;
    }


}
