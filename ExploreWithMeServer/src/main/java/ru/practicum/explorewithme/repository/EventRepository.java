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

    @Query("select e from Event e where (lower(e.annotation) like lower(concat('%', :text, '%')) or " +
            "lower(e.description) like lower(concat('%', :text, '%')))" +
            "AND ((:categoryIds) IS NULL OR e.category.id IN :categoryIds) " +
            "and e.isPaid = :isPaid " +
            "and e.eventDate between :rangeStart and :rangeEnd")
    List<Event> findEvents(String text, Set<Integer> categoryIds, Boolean isPaid, LocalDateTime rangeStart, LocalDateTime rangeEnd);

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
