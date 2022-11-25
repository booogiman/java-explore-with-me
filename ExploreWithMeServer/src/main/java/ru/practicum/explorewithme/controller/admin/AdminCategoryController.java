package ru.practicum.explorewithme.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.category.CategoryDto;
import ru.practicum.explorewithme.dto.category.NewCategoryDto;
import ru.practicum.explorewithme.service.CategoriesService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminCategoryController {

    private final CategoriesService categoriesService;

    @PatchMapping("/categories")
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto) {
        log.info("Администратор обновил категорию={}", categoryDto);
        return categoriesService.updateCategory(categoryDto);
    }

    @PostMapping("/categories")
    public CategoryDto postCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        log.info("Администратор добавил категорию={}", newCategoryDto);
        return categoriesService.addCategory(newCategoryDto);
    }

    @DeleteMapping("/categories/{catId}")
    public void deleteCategory(@PathVariable int catId) {
        log.info("Администратор удалил категорию id={}", catId);
        categoriesService.deleteCategory(catId);
    }
}
