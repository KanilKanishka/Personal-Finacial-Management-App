package com.dissanayake.financeManagement.controller;


import com.dissanayake.financeManagement.entity.Category;
import com.dissanayake.financeManagement.request.CategoryRequest;
import com.dissanayake.financeManagement.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add")
    public String addCategory(CategoryRequest categoryRequest){
        categoryService.addCategory(categoryRequest);
        return "Added new category";
    }

    @GetMapping("/findById")
    public Optional<Category> findCategoryById(Long categoryId){
        return categoryService.findCategoryById(categoryId);
    }

    @GetMapping("/findAll")
    public List<Category> findAllCategory(){
        return categoryService.findAllCategory();
    }

    @PutMapping("/update")
    public String updateCategoryById(@RequestParam("categoryId") Long categoryId,
                                     @RequestBody CategoryRequest categoryRequest){
        categoryService.updateCategoryById(categoryId, categoryRequest);
        return "Category updated";
    }

    @DeleteMapping("/delete")
    public String deleteCategoryById(@RequestParam("categoryId") Long categoryId){
        categoryService.deleteByCategoryId(categoryId);
        return "Category deleted";
    }
}
