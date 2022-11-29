package ru.practicum.explorewithme.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.controller.exceptionHandling.exception.ConditionsNotMetException;
import ru.practicum.explorewithme.controller.exceptionHandling.exception.EntryNotFoundException;
import ru.practicum.explorewithme.dto.comment.CommentDto;
import ru.practicum.explorewithme.dto.comment.UpdateCommentDTO;
import ru.practicum.explorewithme.dto.comment.mapper.CommentMapper;
import ru.practicum.explorewithme.model.Comment;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.model.enumeration.EventState;
import ru.practicum.explorewithme.repository.CommentRepository;
import ru.practicum.explorewithme.service.CommentService;
import ru.practicum.explorewithme.service.EventService;
import ru.practicum.explorewithme.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.practicum.explorewithme.UtilClass.getFormat;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final EventService eventService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional
    public CommentDto createComment(CommentDto commentDto, int userId, int eventId) {
        User user = getUserOrThrow(userId);
        Event event = getEventOrThrow(eventId);
        if (event.getState().equals(EventState.PENDING)) {
            throw new ConditionsNotMetException("Нельзя оставить комментарий к еще неопубликованному событию!");
        }
        commentDto.setCreated(LocalDateTime.now().format(getFormat()));
        Comment comment = CommentMapper.dtoToComment(commentDto, user, event);
        commentRepository.save(comment);
        return CommentMapper.commentToDto(comment);
    }

    @Override
    public List<CommentDto> getAllByUserId(int userId, int from, int size) {
        List<Comment> comments = commentRepository.findAllByAuthor_Id(userId, PageRequest.of(from / size, size));
        return CommentMapper.commentToDtoList(comments);
    }

    @Override
    @Transactional
    public void deleteComment(int commentId) {
        commentRepository.deleteCommentById(commentId);
    }

    @Override
    @Transactional
    public CommentDto editCommentAdmin(CommentDto commentDto) {
        Comment commentToUpdate = getCommentOrThrow(commentDto.getId());
        commentToUpdate.setContent(commentDto.getContent());
        commentRepository.save(commentToUpdate);
        return CommentMapper.commentToDto(commentToUpdate);
    }

    @Override
    @Transactional
    public CommentDto editCommentUser(UpdateCommentDTO updateCommentDTO) {
        Comment commentToUpdate = getCommentOrThrow(updateCommentDTO.getId());
        if (commentToUpdate.getCreatedOn().isBefore(LocalDateTime.now().minusWeeks(1L))) {
            throw new ConditionsNotMetException("Комментарий оставлен слишком давно и вы не можете его отредактировать");
        }
        commentToUpdate.setContent(updateCommentDTO.getContent());
        commentRepository.save(commentToUpdate);
        return CommentMapper.commentToDto(commentToUpdate);
    }

    @Override
    public Comment getCommentOrThrow(int commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new EntryNotFoundException("Отсутствует комментарий с id: " + commentId));
    }

    /**
     * Проверка наличия пользователя в базе
     */
    private User getUserOrThrow(int userId) {
        return userService.getUserOrThrow(userId);
    }

    /**
     * Проверка наличия события в базе
     */
    private Event getEventOrThrow(int eventId) {
        return eventService.getEventOrThrow(eventId);
    }


}
