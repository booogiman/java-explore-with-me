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

import static ru.practicum.explorewithme.UtilClass.getFormat;

public class CommentMapper {


    public static CommentDto commentToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreatedOn().format(getFormat()))
                .build();
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
        comment.setCreatedOn(LocalDateTime.parse(commentDto.getCreated(), getFormat()));
        return comment;
    }
}