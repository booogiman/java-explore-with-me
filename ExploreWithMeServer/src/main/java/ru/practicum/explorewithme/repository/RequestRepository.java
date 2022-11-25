package ru.practicum.explorewithme.repository;

import ru.practicum.explorewithme.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findAllByRequesterId(int requesterId);
}
