package ru.practicum.explorewithme.dto.event.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.dto.category.mapper.CategoryMapper;
import ru.practicum.explorewithme.dto.comment.mapper.CommentMapper;
import ru.practicum.explorewithme.dto.event.*;
import ru.practicum.explorewithme.dto.location.mapper.LocationMapper;
import ru.practicum.explorewithme.dto.user.mapper.UserMapper;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.Request;
import ru.practicum.explorewithme.model.StatEntry;
import org.springframework.lang.Nullable;
import ru.practicum.explorewithme.model.enumeration.RequestState;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.practicum.explorewithme.UtilClass.getFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EventShortDto eventToShortDto(Event event, StatEntry statEntry) {
        return new EventShortDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.categoryToDto(event.getCategory()),
                event.getRequests().size(),
                event.getEventDate().format(getFormat()),
                UserMapper.userToShortDto(event.getInitiator()),
                event.getIsPaid(),
                event.getTitle(),
                statEntry != null ? statEntry.getHits() : 0
        );
    }

    public static EventFullDto eventToFullDto(Event event, @Nullable List<Request> requestList, @Nullable StatEntry statEntry) {
        return new EventFullDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.categoryToDto(event.getCategory()),
                requestList != null ? requestList.stream().filter(request1 -> request1.getState() == RequestState.CONFIRMED).count() : 0,
                event.getEventDate().format(getFormat()),
                UserMapper.userToShortDto(event.getInitiator()),
                event.getIsPaid(),
                event.getTitle(),
                statEntry != null ? statEntry.getHits() : 0,
                event.getCreatedOn().format(getFormat()),
                event.getDescription(),
                LocationMapper.locationToDto(event.getLocation()),
                event.getParticipantLimit(),
                event.getPublishedOn() != null ? event.getPublishedOn().format(getFormat()) : null,
                event.getRequestModeration(),
                event.getState().toString(),
                event.getComments() != null ? CommentMapper.commentToDtoList(event.getComments()) : null
        );
    }

    public static Event dtoToEvent(NewEventDto newEventDto) {
        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setDescription(newEventDto.getDescription());
        event.setEventDate(LocalDateTime.parse(newEventDto.getEventDate(), getFormat()));
        event.setIsPaid(newEventDto.isPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.isRequestModeration());
        event.setTitle(newEventDto.getTitle());
        event.setLocation(LocationMapper.dtoToLocation(newEventDto.getLocation()));
        return event;
    }

    public static Event dtoToUpdateEvent(EventUpdateDto eventUpdateDto, Category category) {
        Event event = new Event();
        event.setAnnotation(eventUpdateDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(eventUpdateDto.getDescription());
        event.setEventDate(LocalDateTime.parse(eventUpdateDto.getEventDate(), getFormat()));
        event.setIsPaid(eventUpdateDto.getPaid());
        event.setId(eventUpdateDto.getEventId());
        event.setParticipantLimit(eventUpdateDto.getParticipantLimit());
        event.setTitle(eventUpdateDto.getTitle());
        return event;
    }

    public static Event dtoToUpdateEventAdmin(AdminUpdateEventRequestDto adminUpdateEventRequestDto, Category category, int eventId) {
        Event event = new Event();
        event.setAnnotation(adminUpdateEventRequestDto.getAnnotation());
        event.setCategory(category);
        event.setDescription(adminUpdateEventRequestDto.getDescription());
        event.setEventDate(LocalDateTime.parse(adminUpdateEventRequestDto.getEventDate(), getFormat()));
        event.setIsPaid(adminUpdateEventRequestDto.getPaid());
        event.setId(eventId);
        event.setParticipantLimit(adminUpdateEventRequestDto.getParticipantLimit());
        event.setTitle(adminUpdateEventRequestDto.getTitle());
        event.setLocation(adminUpdateEventRequestDto.getLocation() != null ? LocationMapper.dtoToLocation(adminUpdateEventRequestDto.getLocation()) : null);
        return event;
    }
}
