package ru.practicum.explorewithme.repository;

import ru.practicum.explorewithme.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
}
