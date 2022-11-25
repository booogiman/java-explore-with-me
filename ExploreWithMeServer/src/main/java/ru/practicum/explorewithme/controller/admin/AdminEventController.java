package ru.practicum.explorewithme.controller.admin;


import ru.practicum.explorewithme.dto.comment.CommentDto;
import ru.practicum.explorewithme.dto.event.AdminUpdateEventRequestDto;
import ru.practicum.explorewithme.dto.event.EventFullDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.service.CommentService;
import ru.practicum.explorewithme.service.EventService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminEventController {

    private final EventService eventService;

    private final CommentService commentService;

    @GetMapping("/events")
    public List<EventFullDto> getEventsAdmin(@RequestParam int[] users,
                                             @RequestParam String[] states,
                                             @RequestParam int[] categories,
                                             @RequestParam String rangeStart,
                                             @RequestParam String rangeEnd,
                                             @RequestParam int from,
                                             @RequestParam int size) {
        log.info("Администратор запросил список событий с параметрами users={}, states={}, categories={}, rangeStart={}, rangeEnd={}, from={}, size={}",
                users, states, categories, rangeStart, rangeEnd, from, size);
        return eventService.getEventsAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/events/{eventId}")
    public EventFullDto updateEventAdmin(@PathVariable int eventId,
                                         @RequestBody AdminUpdateEventRequestDto adminUpdateEventRequestDto) {
        log.info("Администратор обновил событие id={}, adminUpdateEventRequestDto={}",
                eventId, adminUpdateEventRequestDto);
        return eventService.updateEventAdmin(eventId, adminUpdateEventRequestDto);
    }

    @PatchMapping("/events/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable int eventId) {
        log.info("Администратор опубликовал событие id={}", eventId);
        return eventService.publishEvent(eventId);
    }

    @PatchMapping("/events/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable int eventId) {
        log.info("Администратор отклонил событие id={}", eventId);
        return eventService.rejectEvent(eventId);
    }

    @PatchMapping("/events/{eventId}/comments/{commentId}")
    public CommentDto updateComment(@PathVariable int commentId,
                                    @RequestBody CommentDto commentDto) {
        log.info("Администратор отредактировал комментарий id={}, comment={}", commentId, commentDto);
        return commentService.editCommentAdmin(commentDto);
    }

    @DeleteMapping("/events/{eventId}/comments/{commentId}")
    public void deleteComment(@PathVariable int commentId) {
        log.info("Администратор удалил комментарий id={}", commentId);
        commentService.deleteComment(commentId);
    }
}
