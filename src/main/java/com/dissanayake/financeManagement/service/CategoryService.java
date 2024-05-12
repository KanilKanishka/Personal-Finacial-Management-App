package com.dissanayake.financeManagement.service;

import com.dissanayake.financeManagement.entity.Category;
import com.dissanayake.financeManagement.repository.CategoryRepository;
import com.dissanayake.financeManagement.request.CategoryRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void addCategory(CategoryRequest categoryRequest){
        Category category = new Category();
        category.setName(category.getName());

        try{
            categoryRepository.save(category);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Category> findAllCategory(){
        return categoryRepository.findAll();
    }

    public Optional<Category> findCategoryById(Long categoryId){
        boolean isPresent = categoryRepository.findById(categoryId).isPresent();

        if (isPresent){
            return categoryRepository.findById(categoryId);
        }

        return Optional.empty();
    }

    public void updateCategoryById(Long categoryId, CategoryRequest categoryRequest) throws EntityNotFoundException {
        Category category = findCategoryById(categoryId).orElseThrow(() ->
                new EntityNotFoundException("Entity not found."));

        category.setName(categoryRequest.getClass().getName());
        categoryRepository.save(category);
    }

    public void deleteByCategoryId(Long categoryId){
        categoryRepository.deleteById(categoryId);
    }
}
