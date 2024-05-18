package com.budgetapp.service;

import com.budgetapp.entity.Category;
import com.budgetapp.repository.CategoryRepository;
import com.budgetapp.request.CategoryRequest;
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
        category.setName(categoryRequest.getName());

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

    public Category updateCategoryById(Long categoryId, CategoryRequest categoryRequest) throws EntityNotFoundException {
        Category category = findCategoryById(categoryId).orElseThrow(() ->
                new EntityNotFoundException("Entity not found."));

        category.setName(categoryRequest.getClass().getName());
        return categoryRepository.save(category);
    }

    public void deleteByCategoryId(Long categoryId){
        categoryRepository.deleteById(categoryId);
    }
}
