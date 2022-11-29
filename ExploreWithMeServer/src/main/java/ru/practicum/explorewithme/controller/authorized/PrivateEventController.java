package ru.practicum.explorewithme.controller.authorized;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.comment.CommentDto;
import ru.practicum.explorewithme.dto.comment.UpdateCommentDTO;
import ru.practicum.explorewithme.dto.event.EventFullDto;
import ru.practicum.explorewithme.dto.event.EventShortDto;
import ru.practicum.explorewithme.dto.event.EventUpdateDto;
import ru.practicum.explorewithme.dto.event.NewEventDto;
import ru.practicum.explorewithme.dto.request.ParticipationRequestDto;
import ru.practicum.explorewithme.service.EventService;
import ru.practicum.explorewithme.service.RequestService;
import ru.practicum.explorewithme.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/users")
public class PrivateEventController {
    private final EventService eventService;
    private final RequestService requestService;
    private final CommentService commentService;

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getEventsOfUserById(@PathVariable int userId,
                                                   @RequestParam(defaultValue = "0") int from,
                                                   @RequestParam(defaultValue = "10") int size) {
        log.info("Запрошены события пользователя id={}, страница={}, размер={}", userId, from, size);
        return eventService.getEventListByUserId(userId, from, size);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto updateEvent(@PathVariable @Positive int userId,
                                    @Valid @RequestBody EventUpdateDto eventUpdateDto) {
        log.info("Пользователь id={} обновил событие={}", userId, eventUpdateDto);
        return eventService.updateEvent(eventUpdateDto, userId);
    }

    @PostMapping("/{userId}/events")
    public EventFullDto postEvent(@PathVariable @Positive int userId,
                                  @Valid @RequestBody NewEventDto newEventDto) {
        log.info("Пользователь id={} создал событие={}", userId, newEventDto);
        return eventService.addEvent(newEventDto, userId);
    }

    @PostMapping("/{userId}/events/{eventId}/comments")
    public CommentDto createComment(@PathVariable int userId,
                                    @PathVariable int eventId,
                                    @RequestBody CommentDto commentDto) {
        log.info("Пользователь id={} запостил комментарий={} к событию id={}", userId, commentDto, eventId);
        return commentService.createComment(commentDto, userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/comments/{commentId}")
    public CommentDto updateComment(@PathVariable int userId,
                                    @PathVariable int commentId,
                                    @PathVariable int eventId,
                                    @RequestBody UpdateCommentDTO updateCommentDTO) {
        log.info("Пользователь id={} отредактировал комментарий id={} к событию id={}, comment={}"
                , updateCommentDTO.getAuthorID(), updateCommentDTO.getId()
        ,updateCommentDTO.getEventID(),updateCommentDTO.getContent());
        return commentService.editCommentUser(updateCommentDTO);
    }

    @GetMapping("/{userId}/comments")
    public List<CommentDto> getCommentsForUser(@PathVariable int userId,
                                               @RequestParam(defaultValue = "0") int from,
                                               @RequestParam(defaultValue = "10") int size) {
        log.info("Запрошены комментарии пользователя id={} from={} size={}", userId, from, size);
        return commentService.getAllByUserId(userId, from, size);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getFullEventById(@PathVariable int userId,
                                         @PathVariable int eventId) {
        log.info("Пользователь id={} запросил событие id={}", userId, eventId);
        return eventService.getFullEventById(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto cancelEvent(@PathVariable int userId,
                                    @PathVariable int eventId) {
        log.info("Пользователь id={} отменил событие id={}", userId, eventId);
        return eventService.cancelEvent(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestForEventByUserId(@PathVariable int userId,
                                                                    @PathVariable int eventId) {
        log.info("Пользователь id={} запросил список запросов к событию id={}", userId, eventId);
        return requestService.getRequestForEventByUserId(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable int userId,
                                                  @PathVariable int eventId,
                                                  @PathVariable int reqId) {
        log.info("Пользователь id={}, подтвердил запрос на участие id={}, в событии id={}", userId, reqId, eventId);
        return requestService.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable int userId,
                                                  @PathVariable int eventId,
                                                  @PathVariable int reqId) {
        log.info("Пользователь id={}, отклонил запрос на участие id={}, в событии id={}", userId, reqId, eventId);
        return requestService.rejectRequest(userId, eventId, reqId);
    }
}
