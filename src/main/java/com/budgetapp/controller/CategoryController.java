package com.budgetapp.controller;


import com.budgetapp.request.CategoryRequest;
import com.budgetapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;



@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add")
    public Map<String, Object> addCategory(@RequestBody CategoryRequest categoryRequest){
        Map<String, Object> response = new HashMap<>();

        try {
            categoryService.addCategory(categoryRequest);
            response.put("STATUS", "SUCCESS");
            response.put("DATA", "ADD_CATEGORY_SUCCESS");
            return response;
        }catch (Exception e){
            response.put("STATUS", "FAILED");
            response.put("DATA", "ADD_CATEGORY_FAILED");
            return response;
        }
    }

    @GetMapping("/findById")
    public Map<String, Object> findCategoryById(@RequestParam("categoryId") Long categoryId){
        Map<String, Object> response = new HashMap<>();

        if (categoryService.findCategoryById(categoryId).isPresent()){
            response.put("STATUS", "SUCCESS");
            response.put("DATA", categoryService.findCategoryById(categoryId));
            return response;
        }else {
            response.put("STATUS", "SUCCESS");
            response.put("DATA", "NOT_FOUND_DATA");
            return response;
        }
    }

    @GetMapping("/findAll")
    public Map<String, Object> findAllCategory(){
        Map<String, Object> response = new HashMap<>();

        if (!categoryService.findAllCategory().isEmpty()){
            response.put("STATUS", "SUCCESS");
            response.put("DATA", categoryService.findAllCategory());
            return response;
        }else {
            response.put("STATUS", "SUCCESS");
            response.put("DATA", "NOT_FOUND_DATA");
            return response;
        }
    }

    @PutMapping("/update")
    public Map<String, Object> updateCategoryById(@RequestParam("categoryId") Long categoryId,
                                     @RequestBody CategoryRequest categoryRequest){
        Map<String, Object> response = new HashMap<>();

        try {
            categoryService.updateCategoryById(categoryId, categoryRequest);
            response.put("STATUS", "SUCCESS");
            response.put("DATA", categoryService.updateCategoryById(categoryId, categoryRequest));
            return response;
        }catch (Exception e){
            response.put("STATUS", "FAILED");
            response.put("DATA", "UPDATE_FAILED");
            return response;
        }
    }

    @DeleteMapping("/delete")
    public Map<String, Object> deleteCategoryById(@RequestParam("categoryId") Long categoryId){
        Map<String, Object> response = new HashMap<>();

        try{
            categoryService.deleteByCategoryId(categoryId);
            response.put("STATUS", "SUCCESS");
            response.put("DATA", "DELETE_SUCCESS");
            return response;
        }catch (Exception e){
            response.put("STATUS", "FAILED");
            response.put("DATA", "DELETE_FAILED");
            return response;
        }
    }
}
