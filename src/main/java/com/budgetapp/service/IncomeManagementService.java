package com.budgetapp.service;


import com.budgetapp.entity.Category;
import com.budgetapp.entity.IncomeManagement;
import com.budgetapp.exception.ResourceNotFoundException;
import com.budgetapp.repository.IncomeManagementRepository;
import com.budgetapp.request.IncomeManagementRequest;
import com.budgetapp.response.IncomeManagementResponse;
import com.budgetapp.response.IncomeStatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeManagementService {

    private final IncomeManagementRepository incomeManagementRepository;
    private final CategoryService categoryService;
    private Double totalIncome;

    private void initializeTotalIncome() {
        this.totalIncome = getTotalIncome();
    }

    public void addIncome(IncomeManagementRequest incomeManagementRequest){

        Category category = categoryService.findCategoryById(incomeManagementRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + incomeManagementRequest.getCategoryId()));


        IncomeManagement incomeManagement = new IncomeManagement();
        incomeManagement.setCategory(category);
        incomeManagement.setDate(incomeManagementRequest.getDate());
        incomeManagement.setDescription(incomeManagementRequest.getDescription());
        incomeManagement.setNote(incomeManagementRequest.getNote());
        incomeManagement.setAmount(incomeManagementRequest.getAmount());

        try {
            incomeManagementRepository.save(incomeManagement);
            initializeTotalIncome();
            updateStatsPercentage();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding income: " + e.getMessage());
        }
    }

    private void updateStatsPercentage() {
        List<IncomeManagement> allIncomeManagement = incomeManagementRepository.findAll();
        for (IncomeManagement oneIncomeManagement : allIncomeManagement) {
            final Double statsPercentage = (oneIncomeManagement.getAmount() / this.totalIncome) * 100;
            oneIncomeManagement.setStatsPercentage(statsPercentage);
            incomeManagementRepository.save(oneIncomeManagement);
        }
    }

    public IncomeManagementResponse findIncomeById(Long incomeId){
        IncomeManagement incomeManagement = incomeManagementRepository.findById(incomeId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found for this id :: " + incomeId));
        return mapToResponse(incomeManagement);
    }


    public List<IncomeManagementResponse> findByDateRange(String fromDate, String toDate){
        List<IncomeManagement> incomeManagementList = incomeManagementRepository.findByDateRange(fromDate, toDate);
        return incomeManagementList.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public Double totalIncomeByDateRange(String fromDate, String toDate){
        return incomeManagementRepository.totalIncomeByDateRange(fromDate, toDate);
    }

    public List<IncomeStatsResponse> incomeStatsFindByDateRange(String fromDate, String toDate) {
        List<IncomeManagement> incomeManagementList = incomeManagementRepository.findByDateRange(fromDate, toDate);
        return incomeManagementList.stream().map(this::mapToIncomeStatsResponse).collect(Collectors.toList());
    }

    public List<IncomeManagementResponse> findAllIncome(){
        List<IncomeManagement> incomeManagementList = incomeManagementRepository.findAll();
        return incomeManagementList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public IncomeManagementResponse updateIncomeById(Long incomeId,
                                             IncomeManagementRequest incomeManagementRequest){

        IncomeManagement incomeManagement = incomeManagementRepository.findById(incomeId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found for this id :: " + incomeId));

        Category category = categoryService.findCategoryById(incomeManagementRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + incomeManagementRequest.getCategoryId()));

        incomeManagement.setDescription(incomeManagementRequest.getDescription());
        incomeManagement.setDate(incomeManagementRequest.getDate());
        incomeManagement.setCategory(category);
        incomeManagement.setNote(incomeManagementRequest.getNote());
        incomeManagement.setAmount(incomeManagementRequest.getAmount());

        IncomeManagement updatedIncome = incomeManagementRepository.save(incomeManagement);
        return mapToResponse(updatedIncome);
    }

    public void deleteIncomeById(Long incomeId){
        incomeManagementRepository.deleteById(incomeId);
    }

    public Double getTotalIncome(){
        return incomeManagementRepository.getTotalIncome();
    }

    private IncomeManagementResponse mapToResponse(IncomeManagement incomeManagement) {
        return new IncomeManagementResponse(
                incomeManagement.getDate(),
                incomeManagement.getAmount(),
                incomeManagement.getCategory(),
                incomeManagement.getNote(),
                incomeManagement.getDescription()
        );
    }

    private IncomeStatsResponse mapToIncomeStatsResponse(IncomeManagement incomeManagement) {
        return new IncomeStatsResponse(
                incomeManagement.getAmount(),
                incomeManagement.getCategory(),
                incomeManagement.getStatsPercentage(),
                incomeManagement.getDate()
        );
    }
}