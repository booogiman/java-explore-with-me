package ru.practicum.explorewithme.repository;

import ru.practicum.explorewithme.model.StatHit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<StatHit, Integer> {
    List<StatHit> findAllByUri(String uri);

    @Query(value = "SELECT DISTINCT s.app, s.uri, s.ip FROM StatHit as s WHERE s.uri = ?1")
    List<StatHit> findDistinctByUriAndIpAndApp(String uri);

    List<StatHit> findAllByUriAndTimestampBetween(String uri, LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT DISTINCT s.app, s.uri, s.ip FROM StatHit as s WHERE s.uri = ?1 AND s.timestamp BETWEEN ?2 AND ?3")
    List<StatHit> findDistinctByUriAndTimestampBetween(String uri, LocalDateTime start, LocalDateTime end);

}
