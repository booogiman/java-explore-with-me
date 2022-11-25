package ru.practicum.explorewithme.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.client.StatisticsClient;
import ru.practicum.explorewithme.controller.exceptionHandling.exception.ConditionsNotMetException;
import ru.practicum.explorewithme.controller.exceptionHandling.exception.EntryNotFoundException;
import ru.practicum.explorewithme.dto.event.*;
import ru.practicum.explorewithme.dto.event.mapper.EventMapper;
import ru.practicum.explorewithme.dto.statistic.StatHitDto;
import lombok.RequiredArgsConstructor;
import ru.practicum.explorewithme.model.*;
import ru.practicum.explorewithme.model.enumeration.EventState;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.service.CategoriesService;
import ru.practicum.explorewithme.service.EventService;
import ru.practicum.explorewithme.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final CategoriesService categoriesService;
    private final UserService userService;
    private final StatisticsClient statisticsClient;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public EventFullDto getEventById(HttpServletRequest request, int eventId) {
        sendStatHit(request); //отправка информации на сервер статистики
        Event event = getEventOrThrow(eventId);
        return EventMapper.eventToFullDto(event, event.getRequests(), getStatForEvent(eventId));
    }

    @Override
    public List<EventShortDto> getEvents(HttpServletRequest request,
                                         String text,
                                         int[] categories,
                                         Boolean isPaid,
                                         String rangeStart,
                                         String rangeEnd,
                                         boolean onlyAvailable,
                                         String sort,
                                         int from,
                                         int size) {
        sendStatHit(request); //отправка информации на сервер статистики
        LocalDateTime dateStart = rangeStart == null || rangeStart.isEmpty() ?
                LocalDateTime.now() :
                LocalDateTime.parse(rangeStart, formatter);
        LocalDateTime dateEnd = rangeEnd == null || rangeEnd.isEmpty() ?
                null :
                LocalDateTime.parse(rangeEnd, formatter);
        Set<Integer> categorySet = categories == null ?
                new HashSet<>() :
                Arrays.stream(categories).boxed().collect(Collectors.toSet());
        List<Event> events = eventRepository.findEventsByParams(
                text != null && !text.isEmpty(),
                "%" + text + "%",
                !categorySet.isEmpty(),
                categorySet,
                isPaid != null,
                isPaid,
                dateEnd == null,
                dateStart,
                dateEnd == null ? dateStart : dateEnd,
                onlyAvailable,
                PageRequest.of(from / size, size)
        );
        List<EventShortDto> eventShortDtos = new ArrayList<>();
        mapEventsToShortDto(events, eventShortDtos);
        if (sort.equals("VIEWS")) {
            eventShortDtos.sort(Comparator.comparing(EventShortDto::getViews));
        }
        return eventShortDtos;
    }

    @Override
    public List<EventFullDto> getEventsAdmin(int[] users,
                                             String[] states,
                                             int[] categories,
                                             String rangeStart,
                                             String rangeEnd,
                                             int from,
                                             int size) {
        LocalDateTime dateStart = rangeStart == null || rangeStart.isEmpty() ?
                LocalDateTime.now() :
                LocalDateTime.parse(rangeStart, formatter);
        LocalDateTime dateEnd = rangeEnd == null || rangeEnd.isEmpty() ?
                null :
                LocalDateTime.parse(rangeEnd, formatter);
        Set<Integer> userSet = users == null ?
                new HashSet<>() :
                Arrays.stream(users).boxed().collect(Collectors.toSet());
        Set<Integer> stateSet = states == null ?
                new HashSet<>() :
                Arrays.stream(states).map(state -> EventState.valueOf(state).ordinal()).collect(Collectors.toSet());
        Set<Integer> categorySet = categories == null ?
                new HashSet<>() :
                Arrays.stream(categories).boxed().collect(Collectors.toSet());
        List<Event> events = eventRepository.findEventsByParamsAdmin(
                !userSet.isEmpty(),
                userSet,
                !stateSet.isEmpty(),
                stateSet,
                !categorySet.isEmpty(),
                categorySet,
                dateEnd == null,
                dateStart,
                dateEnd == null ? dateStart : dateEnd,
                PageRequest.of(from / size, size)
        );
        List<EventFullDto> eventFullDtos = new ArrayList<>();
        for (Event event : events) {
            eventFullDtos.add(EventMapper.eventToFullDto(event, event.getRequests(), getStatForEvent(event.getId())));
        }
        return eventFullDtos;
    }

    @Override
    @Transactional
    public EventFullDto addEvent(NewEventDto newEventDto, int userId) {
        if (LocalDateTime.parse(newEventDto.getEventDate(), formatter).equals(LocalDateTime.now().plusHours(2))) {
            throw new ConditionsNotMetException("Дата события не может быть раньше, чем через два часа");
        }
        User user = getUserOrThrow(userId); //проверка того, что такой пользователь существует
        Category category = getCategoryOrThrow(newEventDto.getCategory()); //проверка наличия категории
        Event event = EventMapper.dtoToEvent(newEventDto);
        event.setInitiator(user);
        event.setCategory(category);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(EventState.PENDING);
        eventRepository.save(event);
        return EventMapper.eventToFullDto(event, null, null);
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(EventUpdateDto eventUpdateDto, int userId) {
        getUserOrThrow(userId); //проверка наличия пользователя
        Event eventToUpdate = getEventOrThrow(eventUpdateDto.getEventId());
        checkEventInitiator(eventToUpdate, userId);
        Category updatedCategory = new Category();
        if (eventUpdateDto.getCategory() != null) {
            updatedCategory = getCategoryOrThrow(eventUpdateDto.getCategory());
        }
        if (eventToUpdate.getState().equals(EventState.PUBLISHED)) {
            throw new ConditionsNotMetException("Нельзя изменить опубликованное событие");
        }
        LocalDateTime eventDate = LocalDateTime.parse(eventUpdateDto.getEventDate(), formatter);
        if (eventDate.equals(LocalDateTime.now().plusHours(2))) {
            throw new ConditionsNotMetException("Дата события не может быть раньше, чем через два часа");
        }
        if (eventToUpdate.getState().equals(EventState.CANCELED)) {
            eventToUpdate.setState(EventState.PENDING);
        }
        Event updatedEvent = EventMapper.dtoToUpdateEvent(eventUpdateDto, updatedCategory);
        if (updatedEvent.getAnnotation() != null) {
            eventToUpdate.setAnnotation(updatedEvent.getAnnotation());
        }
        if (updatedEvent.getCategory() != null) {
            eventToUpdate.setCategory(updatedEvent.getCategory());
        }
        if (updatedEvent.getDescription() != null) {
            eventToUpdate.setDescription(updatedEvent.getDescription());
        }
        if (updatedEvent.getEventDate() != null) {
            eventToUpdate.setEventDate(LocalDateTime.parse(eventUpdateDto.getEventDate(), formatter));
        }
        if (updatedEvent.getIsPaid() != null) {
            eventToUpdate.setIsPaid(eventUpdateDto.getPaid());
        }
        if (updatedEvent.getParticipantLimit() != null) {
            eventToUpdate.setParticipantLimit(eventUpdateDto.getParticipantLimit());
        }
        if (updatedEvent.getTitle() != null) {
            eventToUpdate.setTitle(eventToUpdate.getTitle());
        }
        return EventMapper.eventToFullDto(eventToUpdate, eventToUpdate.getRequests(), getStatForEvent(eventToUpdate.getId()));
    }

    @Override
    public EventFullDto getFullEventById(int userId, int eventId) {
        getUserOrThrow(userId);
        Event event = getEventOrThrow(eventId);
        checkEventInitiator(event, userId);
        return EventMapper.eventToFullDto(event, event.getRequests(), getStatForEvent(eventId));
    }

    @Override
    public List<EventShortDto> getEventListByUserId(int userId, int from, int size) {
        User user = getUserOrThrow(userId);
        List<Event> eventList = eventRepository.findEventsByInitiator(user, PageRequest.of(from / size, size));
        List<EventShortDto> eventShortDtoList = new ArrayList<>();
        if (eventList.size() != 0) {
            mapEventsToShortDto(eventList, eventShortDtoList);
        }
        return eventShortDtoList;
    }

    @Override
    @Transactional
    public EventFullDto cancelEvent(int userId, int eventId) {
        getUserOrThrow(userId);
        Event event = getEventOrThrow(eventId);
        checkEventInitiator(event, userId);
        if (!event.getState().equals(EventState.PENDING)) {
            throw new ConditionsNotMetException("Отменить можно только событие в состоянии ожидания модерации");
        }
        event.setState(EventState.CANCELED);
        return EventMapper.eventToFullDto(event, event.getRequests(), getStatForEvent(eventId));
    }

    @Override
    @Transactional
    public EventFullDto updateEventAdmin(int eventId, AdminUpdateEventRequestDto adminUpdateEventRequestDto) {
        Event eventToUpdate = getEventOrThrow(eventId);
        Category updatedCategory = new Category();
        if (adminUpdateEventRequestDto.getCategory() != null) {
            updatedCategory = getCategoryOrThrow(adminUpdateEventRequestDto.getCategory());
        }
        Event updatedEvent = EventMapper.dtoToUpdateEventAdmin(adminUpdateEventRequestDto, updatedCategory, eventId);
        if (updatedEvent.getAnnotation() != null) {
            eventToUpdate.setAnnotation(updatedEvent.getAnnotation());
        }
        if (updatedEvent.getCategory() != null) {
            eventToUpdate.setCategory(updatedEvent.getCategory());
        }
        if (updatedEvent.getDescription() != null) {
            eventToUpdate.setDescription(updatedEvent.getDescription());
        }
        if (updatedEvent.getEventDate() != null) {
            eventToUpdate.setEventDate(updatedEvent.getEventDate());
        }
        if (updatedEvent.getLocation() != null) {
            eventToUpdate.setLocation(updatedEvent.getLocation());
        }
        if (updatedEvent.getIsPaid() != null) {
            eventToUpdate.setIsPaid(updatedEvent.getIsPaid());
        }
        if (updatedEvent.getParticipantLimit() != null) {
            eventToUpdate.setParticipantLimit(updatedEvent.getParticipantLimit());
        }
        if (updatedEvent.getRequestModeration() != null) {
            eventToUpdate.setRequestModeration(updatedEvent.getRequestModeration());
        }
        if (updatedEvent.getTitle() != null) {
            eventToUpdate.setTitle(updatedEvent.getTitle());
        }
        return EventMapper.eventToFullDto(eventToUpdate, eventToUpdate.getRequests(), getStatForEvent(eventToUpdate.getId()));
    }

    @Override
    @Transactional
    public EventFullDto publishEvent(int eventId) {
        Event event = getEventOrThrow(eventId);
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1L))) {
            throw new ConditionsNotMetException("Нельзя опубликовать событие, до которого осталось менее часа.");
        }
        if (!event.getState().equals(EventState.PENDING)) {
            throw new ConditionsNotMetException("Нельзя опубликовать событие, не находящееся в состоянии ожидания.");
        }
        event.setPublishedOn(LocalDateTime.now());
        return changeState(eventId, EventState.PUBLISHED);
    }

    @Override
    @Transactional
    public EventFullDto rejectEvent(int eventId) {
        Event event = getEventOrThrow(eventId);
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ConditionsNotMetException("Нельзя отклонить уже опубликованное событие.");
        }
        return changeState(eventId, EventState.CANCELED);
    }

    private EventFullDto changeState(int eventId, EventState state) {
        Event event = getEventOrThrow(eventId);
        event.setState(state);
        return EventMapper.eventToFullDto(event, event.getRequests(), getStatForEvent(eventId));
    }

    /**
     * Собираем объект для отправки в сервис статистики.
     *
     * @param request - содержит в себе данные для сервиса статистики
     */
    private void sendStatHit(HttpServletRequest request) {
        StatHitDto statHitDto = new StatHitDto(
                request.getServerName(),
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now().format(formatter)
        );
        statisticsClient.sendHit(statHitDto);
    }

    /**
     * Проверка наличия события в базе
     */
    @Override
    public Event getEventOrThrow(int eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new EntryNotFoundException("Отсутствует событие с id: " + eventId));
    }

    @Override
    public List<Event> getEventsByCategory(Category category) {
        return eventRepository.findEventsByCategory(category);
    }

    @Override
    public List<Event> getAllById(List<Integer> eventIdList) {
        return eventRepository.findAllById(eventIdList);
    }

    /**
     * Проверка наличия категории события в базе
     */
    private Category getCategoryOrThrow(int categoryId) {
        return categoriesService.getCategoryOrThrow(categoryId);
    }

    /**
     * Проверка наличия пользователя в базе
     */
    private User getUserOrThrow(int userId) {
        return userService.getUserOrThrow(userId);
    }

    private StatEntry getStatForEvent(int eventId) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return statisticsClient.getEventStat(eventId,
                localDateTime.format(formatter),
                localDateTime.format(formatter));
    }

    private void checkEventInitiator(Event event, int userId) {
        if (event.getInitiator().getId() != userId) {
            throw new ConditionsNotMetException("Это не ваше событие");
        }
    }

    private void mapEventsToShortDto(List<Event> eventList, List<EventShortDto> eventShortDtoList) {
        for (Event event : eventList) {
            eventShortDtoList.add(EventMapper.eventToShortDto(event, getStatForEvent(event.getId())));
        }
    }
}
