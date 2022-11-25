package ru.practicum.explorewithme.controller.unauthorized;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.category.CategoryDto;
import ru.practicum.explorewithme.service.CategoriesService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {

    private final CategoriesService categoriesService;

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam int from,
                                           @RequestParam int size) {
        log.info("Запрошены все категории, страница={}, размер={}", from, size);
        return categoriesService.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable int catId) {
        log.info("Запрошена категория id={}", catId);
        return categoriesService.getCategoryById(catId);
    }
}
