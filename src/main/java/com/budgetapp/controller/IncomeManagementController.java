package com.budgetapp.controller;


import com.budgetapp.request.IncomeManagementRequest;
import com.budgetapp.service.IncomeManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/income/management")
public class IncomeManagementController {

    private final IncomeManagementService incomeManagementService;

    @PostMapping("/add")
    public Map<String, Object> addIncome(@RequestBody IncomeManagementRequest incomeManagementRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            incomeManagementService.addIncome(incomeManagementRequest);
            response.put("STATUS", "SUCCESS");
            response.put("DATA", "ADD_INCOME_SUCCESS");
            return response;
        }catch (Exception e){
            e.printStackTrace();
            response.put("STATUS", "FAILED");
            response.put("DATA", "ADD_INCOME_FAILED");
            return response;
        }
    }

    @GetMapping("/findById")
    public Map<String, Object> findIncomeById(@RequestParam("incomeId") Long incomeId){
        Map<String, Object> response = new HashMap<>();

        try{
            incomeManagementService.findIncomeById(incomeId);
            response.put("STATUS", "SUCCESS");
            response.put("DATA", incomeManagementService.findIncomeById(incomeId));
            return response;
        }catch (Exception e){
            e.printStackTrace();
            response.put("STATUS", "SUCCESS");
            response.put("DATA", "NOT_FOUND_DATA");
            return response;
        }
    }

    @GetMapping("/findByDateRange")
    public Map<String, Object> findByDateRange(@RequestParam("fromDate") String fromDate,
                                                  @RequestParam("toDate") String toDate){

        Map<String, Object> response = new HashMap<>();

        if (!incomeManagementService.findByDateRange(fromDate, toDate).isEmpty()){
            response.put("STATUS", "SUCCESS");
            response.put("DATA", incomeManagementService.findByDateRange(fromDate, toDate));
            response.put("TOTAL_INCOME_AMOUNT", incomeManagementService.totalIncomeByDateRange(fromDate, toDate));
            return response;
        }else {
            response.put("STATUS", "SUCCESS");
            response.put("DATA", "NOT_FOUND_DATA");
            return response;
        }
    }

    @GetMapping("/findAll")
    public Map<String, Object> findAllIncome(){
        Map<String, Object> response = new HashMap<>();

        if (!incomeManagementService.findAllIncome().isEmpty()){
            response.put("STATUS", "SUCCESS");
            response.put("DATA", incomeManagementService.findAllIncome());
            response.put("TOTAL_INCOME_AMOUNT", incomeManagementService.getTotalIncome());
            return response;
        }else {
            response.put("STATUS", "SUCCESS");
            response.put("DATA", "NOT_FOUND_DATA");
            return response;
        }
    }

    @PutMapping("/update")
    public Map<String, Object> updateIncomeById(
            @RequestParam("incomeId") Long incomeId,
            @RequestBody IncomeManagementRequest incomeManagementRequest){

        Map<String, Object> response = new HashMap<>();

        try {
            incomeManagementService.updateIncomeById(incomeId, incomeManagementRequest);
            response.put("STATUS", "SUCCESS");
            response.put("DATA", incomeManagementService.updateIncomeById(incomeId, incomeManagementRequest));
            return response;
        }catch (Exception e){
            e.printStackTrace();
            response.put("STATUS", "FAILED");
            response.put("DATA", "UPDATE_FAILED");
            return response;
        }
    }

    @DeleteMapping("/delete")
    public Map<String, Object> deleteIncomeById(@RequestParam("incomeId") Long incomeId){
        Map<String, Object> response = new HashMap<>();

        try{
            incomeManagementService.deleteIncomeById(incomeId);
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
