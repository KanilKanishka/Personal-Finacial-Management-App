package com.bugetapp.service;


import com.bugetapp.repository.IncomeManagementRepository;
import com.bugetapp.entity.IncomeManagement;
import com.bugetapp.request.IncomeManagementRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IncomeManagementService {

    private final IncomeManagementRepository incomeManagementRepository;

    public void addIncome(IncomeManagementRequest incomeManagementRequest){
        IncomeManagement incomeManagement = new IncomeManagement();
        incomeManagement.setCategories(incomeManagementRequest.getCategory());
        incomeManagement.setDate(incomeManagementRequest.getDate());
        incomeManagement.setDescription(incomeManagementRequest.getDescription());
        incomeManagement.setAmount(incomeManagementRequest.getAmount());

        incomeManagementRepository.save(incomeManagement);
    }

    public Optional<IncomeManagement> findIncomeById(Long incomeId){
        boolean isPresent = incomeManagementRepository.findById(incomeId).isPresent();
        if (isPresent){
            return incomeManagementRepository.findById(incomeId);
        }
        return Optional.empty();
    }

    public List<IncomeManagement> findByDateRange(String fromDate, String toDate){
        return incomeManagementRepository.findByDateRange(fromDate, toDate);
    }

    public List<IncomeManagement> findAllIncome(){
        return incomeManagementRepository.findAll();
    }

    public void updateIncomeById(Long incomeId, IncomeManagementRequest incomeManagementRequest){

        IncomeManagement incomeManagement = findIncomeById(incomeId).orElseThrow(() ->
                new EntityNotFoundException("Entity not found."));

        incomeManagement.setDescription(incomeManagementRequest.getDescription());
        incomeManagement.setNote(incomeManagementRequest.getNote());
        incomeManagement.setAmount(incomeManagementRequest.getAmount());
        incomeManagement.setDate(incomeManagementRequest.getDate());

        incomeManagementRepository.save(incomeManagement);
    }

    public void deleteIncomeById(Long incomeId){
        incomeManagementRepository.deleteById(incomeId);
    }
}
