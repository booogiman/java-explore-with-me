package ru.practicum.explorewithme.repository;

import ru.practicum.explorewithme.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
