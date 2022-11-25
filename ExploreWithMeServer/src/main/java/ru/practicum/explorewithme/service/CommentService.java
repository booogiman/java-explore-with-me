package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.comment.CommentDto;
import ru.practicum.explorewithme.model.Comment;

import java.util.List;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, int userId, int eventId);

    List<CommentDto> getAllByUserId(int userId, int from, int size);

    void deleteComment(int commentId);

    CommentDto editCommentAdmin(CommentDto commentDto);

    CommentDto editCommentUser(CommentDto commentDto);

    Comment getCommentOrThrow(int commentId); //служебный метод для проверки наличия комментария в базе
}
