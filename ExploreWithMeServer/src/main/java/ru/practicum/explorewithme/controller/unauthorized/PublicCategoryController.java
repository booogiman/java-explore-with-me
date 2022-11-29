package ru.practicum.explorewithme.controller.unauthorized;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.category.CategoryDto;
import ru.practicum.explorewithme.service.CategoriesService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/categories")
public class PublicCategoryController {

    private final CategoriesService categoriesService;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(name = "from", defaultValue = "0")
                                           @PositiveOrZero int from,
                                           @RequestParam(name = "size", defaultValue = "10")
                                           @Positive int size) {
        log.info("Запрошены все категории, страница={}, размер={}", from, size);
        return categoriesService.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable @Positive int catId) {
        log.info("Запрошена категория id={}", catId);
        return categoriesService.getCategoryById(catId);
    }
}
