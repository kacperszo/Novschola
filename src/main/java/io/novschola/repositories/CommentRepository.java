package io.novschola.repositories;

import io.novschola.model.Comment;
import io.novschola.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Comment repository
 *
 * @author Kacper Szot
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByPostId(Long id);

    Page<Comment> findAllByAuthorId(Long id);

    Page<Comment> findAllByAuthor(User author);

}
