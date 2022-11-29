package ru.practicum.explorewithme.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.category.CategoryDto;
import ru.practicum.explorewithme.dto.category.NewCategoryDto;
import ru.practicum.explorewithme.service.CategoriesService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/admin")
@Tag(name = "Admin: Категория", description = "API для работы с категориями")
public class AdminCategoryController {

    private final CategoriesService categoriesService;

    @Operation(
            summary = "Изменение категории",
            description = "Обратите внимание: имя категории должно быть уникальным"
    )
    @PatchMapping("/categories")
    public CategoryDto updateCategory(@RequestBody @Valid CategoryDto categoryDto) {
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
