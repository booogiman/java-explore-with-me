package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query(value = "SELECT e.* FROM events AS e " +
            "LEFT JOIN (" +
            "   SELECT COUNT(r.id) AS COUNT, event_id FROM requests AS r " +
            "   WHERE r.status = 3 GROUP BY event_id" +
            ") AS rcount ON rcount.event_id = e.id " +
            "WHERE (false = :searchByText OR LOWER(e.annotation) like LOWER(:text) OR LOWER(e.description) like LOWER(:text)) " +
            "AND (false = :searchByCategory OR e.category_id IN :categoryIds) " +
            "AND (false = :searchByIsPaid OR e.is_paid = :isPaid) " +
            "AND (false = :searchByOneDate OR e.event_date > :rangeStart) " +
            "AND (true = :searchByOneDate OR e.event_date between :rangeStart and :rangeEnd) " +
            "AND (false = :searchByOnlyAvailable OR e.participant_limit = 0 OR e.participant_limit > rcount.COUNT)" +
            "ORDER BY event_date DESC",
            nativeQuery = true)
    List<Event> findEventsByParams(
            @Param("searchByText") boolean searchByText,
            @Param("text") String text,
            @Param("searchByCategory") boolean searchByCategory,
            @Param("categoryIds") Set<Integer> categoryIds,
            @Param("searchByIsPaid") boolean searchByIsPaid,
            @Param("isPaid") Boolean isPaid,
            @Param("searchByOneDate") boolean searchByOneDate,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            @Param("searchByOnlyAvailable") boolean searchByOnlyAvailable,
            Pageable pageable
    );

    @Query(value = "SELECT e.* FROM events AS e " +
            "WHERE (false = :searchByUsers OR e.initiator_id IN :userIds) " +
            "AND (false = :searchByStates OR e.status IN :stateIds) " +
            "AND (false = :searchByCategory OR e.category_id IN :categoryIds) " +
            "AND (true = :searchByOneDate OR e.event_date > :rangeStart) " +
            "AND (false = :searchByOneDate OR e.event_date between :rangeStart and :rangeEnd)",
            nativeQuery = true)
    List<Event> findEventsByParamsAdmin(
            @Param("searchByUsers") boolean searchByUsers,
            @Param("userIds") Set<Integer> userIds,
            @Param("searchByStates") boolean searchByStates,
            @Param("stateIds") Set<Integer> stateIds,
            @Param("searchByCategory") boolean searchByCategory,
            @Param("categoryIds") Set<Integer> categoryIds,
            @Param("searchByOneDate") boolean searchByOneDate,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            Pageable pageable
    );

    List<Event> findEventsByInitiator(User initiator, Pageable pageable);

    List<Event> findEventsByCategory(Category category);
}
