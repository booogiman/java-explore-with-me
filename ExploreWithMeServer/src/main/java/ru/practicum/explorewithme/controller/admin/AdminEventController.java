package ru.practicum.explorewithme.controller.admin;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.comment.CommentDto;
import ru.practicum.explorewithme.dto.comment.UpdateCommentDTO;
import ru.practicum.explorewithme.dto.event.AdminUpdateEventRequestDto;
import ru.practicum.explorewithme.dto.event.EventFullDto;
import ru.practicum.explorewithme.service.CommentService;
import ru.practicum.explorewithme.service.EventService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/admin")
public class AdminEventController {

    private final EventService eventService;

    private final CommentService commentService;

    @GetMapping("/events")
    public List<EventFullDto> getEventsAdmin(@RequestParam(required = false) int[] users,
                                             @RequestParam(required = false) String[] states,
                                             @RequestParam(required = false) int[] categories,
                                             @RequestParam(required = false) String rangeStart,
                                             @RequestParam(required = false) String rangeEnd,
                                             @RequestParam(name = "from", defaultValue = "0") int from,
                                             @RequestParam(name = "size", defaultValue = "10") int size) {
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

    @PatchMapping("/events/comments")
    public CommentDto updateComment(@RequestBody UpdateCommentDTO updateCommentDTO) {
        log.info("Администратор отредактировал комментарий id={}, comment={}", updateCommentDTO.getId(), updateCommentDTO.getContent());
        return commentService.editCommentAdmin(updateCommentDTO);
    }

    @DeleteMapping("/events/{eventId}/comments/{commentId}")
    public void deleteComment(@PathVariable int commentId) {
        log.info("Администратор удалил комментарий id={}", commentId);
        commentService.deleteComment(commentId);
    }
}
