package com.bugetapp.controller;


import com.bugetapp.entity.IncomeManagement;
import com.bugetapp.request.IncomeManagementRequest;
import com.bugetapp.service.IncomeManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/income/management")
public class IncomeManagementController {

    private final IncomeManagementService incomeManagementService;

    @PostMapping("/add")
    public String addIncome(@RequestBody IncomeManagementRequest incomeManagementRequest) {
        incomeManagementService.addIncome(incomeManagementRequest);
        return "success";
    }

    @GetMapping("/findById")
    public Optional<IncomeManagement> findIncomeById(@RequestParam("incomeId") Long incomeId){
        return incomeManagementService.findIncomeById(incomeId);
    }

    @GetMapping("/findByDateRange")
    public List<IncomeManagement> findByDateRange(@RequestParam("fromDate") String fromDate,
                                                  @RequestParam("toDate") String toDate){
        return incomeManagementService.findByDateRange(fromDate, toDate);
    }

    @GetMapping("/findAll")
    public List<IncomeManagement> findAllIncome(){
        return incomeManagementService.findAllIncome();
    }

    @PutMapping("/update")
    public String updateIncomeById(
            @RequestParam("incomeId") Long incomeId,
            @RequestBody IncomeManagementRequest incomeManagementRequest){
        incomeManagementService.updateIncomeById(incomeId, incomeManagementRequest);
        return "success";
    }

    @DeleteMapping("/delete")
    public String deleteIncomeById(@RequestParam("incomeId") Long incomeId){
        incomeManagementService.deleteIncomeById(incomeId);
        return "success";
    }

}
