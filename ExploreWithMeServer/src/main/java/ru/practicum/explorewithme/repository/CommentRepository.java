package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Pageable;
import ru.practicum.explorewithme.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByAuthor_Id(int userId, Pageable pageable);

    void deleteCommentById(int commentId);
}
