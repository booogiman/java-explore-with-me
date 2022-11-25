package ru.practicum.explorewithme.dto.comment.mapper;

import ru.practicum.explorewithme.dto.comment.CommentDto;
import ru.practicum.explorewithme.model.Comment;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static CommentDto commentToDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getAuthor().getName(),
                comment.getCreatedOn().format(FORMATTER)
        );
    }

    public static List<CommentDto> commentToDtoList(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return Collections.emptyList();
        }
        comments.sort(Comparator.comparing(Comment::getCreatedOn));
        return comments.stream().map(CommentMapper::commentToDto).collect(Collectors.toList());
    }

    public static Comment dtoToComment(CommentDto commentDto, User user, Event event) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setAuthor(user);
        comment.setEvent(event);
        comment.setCreatedOn(LocalDateTime.parse(commentDto.getCreated(), FORMATTER));
        return comment;
    }
}
