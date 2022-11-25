package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.category.CategoryDto;
import ru.practicum.explorewithme.dto.category.NewCategoryDto;
import ru.practicum.explorewithme.model.Category;

import java.util.List;

public interface CategoriesService {
    CategoryDto addCategory(NewCategoryDto newCategoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

    void deleteCategory(int categoryId);

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategoryById(int categoryId);

    Category getCategoryOrThrow(int categoryId); //слубежный метод для проверки наличия категории в базе
}
