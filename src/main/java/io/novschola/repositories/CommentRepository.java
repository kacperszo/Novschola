package io.novschola.repositories;

import io.novschola.model.Comment;
import io.novschola.model.Post;
import io.novschola.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Comment repository
 *
 * @author Kacper Szot
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long id);

    List<Comment> findAllByAuthorId(Long id);

    List<Comment> findAllByAuthor(User author);

    List<Comment> findAllByPost(Post post);
}
