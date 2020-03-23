package io.novschola.repositories;

import io.novschola.model.Comment;
import io.novschola.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Comment repository
 *
 * @author Kacper Szot
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Iterable<Comment> findAllByPostId(Long id);

    Iterable<Comment> findAllByAuthorId(Long id);

    Iterable<Comment> findAllByAuthor(User author);

}
