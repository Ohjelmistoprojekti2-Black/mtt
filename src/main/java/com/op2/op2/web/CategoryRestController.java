package com.op2.op2.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.op2.op2.domain.Category;
import com.op2.op2.domain.CategoryRepository;
import com.op2.op2.domain.Event;
import com.op2.op2.domain.EventRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin("http://localhost:5173")
@Tag(name="Category REST", description="Names of categories and their descriptions")
public class CategoryRestController {
    private final Logger log = LoggerFactory.getLogger(CategoryRestController.class);

    @Autowired
    private CategoryRepository categoryRepo;
    private EventRepository eventRepo;

    @GetMapping({ "/categories" })
    public @ResponseBody List<Category> findAllCategories(){
        log.info("Fetch all categories");
        try {
            return (List<Category>) categoryRepo.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No categories found");
        }
    }

    @GetMapping("/categories/{name}")
    public @ResponseBody Category getCategoryByName(@PathVariable("name") String categoryName) {
    log.info("Fetch category by name");
    try {
        return categoryRepo.findAllByCategoryName(categoryName);
    } catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a category");
    }
}
    // save new category
    @PostMapping({ "/categories" })
    public @ResponseBody Category saveCategoryRest(@RequestBody Category category){
        return categoryRepo.save(category);
    }

    @DeleteMapping("/categories/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("name") String categoryName) {
        var category = categoryRepo.findById(categoryName).orElse(null);
        var events = category.getEvents();
        
        if (category != null) {
            for (Event event : events) {
                event.setCategory(null);
            }
            categoryRepo.delete(category);
        }
    }

    @PutMapping({ "/categories/{name}" })
    @ResponseStatus(HttpStatus.CREATED)
    public Category updateCategory(@RequestBody Category updatedCategory, @PathVariable("name") String categoryName){
        List<Category> category = categoryRepo.findByCategoryName(categoryName);
        if (category.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find category");
        }
        try{
            updatedCategory.setCategoryName(categoryName);
            return categoryRepo.save(updatedCategory);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Could not accept");
        }
    }
    
}
