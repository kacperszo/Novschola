package io.novschola.repositories;

import io.novschola.model.Post;
import io.novschola.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Post repository
 * @author Kacper Szot
 */
@Repository
public interface PostRepository  extends JpaRepository<Post, Long> {
    Page<Post> findAllByContentContainingOrTitleContaining(String content, String title, Pageable pageable);
    Page<Post> findAllByAuthor(User author, Pageable pageable);
    Page<Post> findAllByAuthor_Id(Long authorId, Pageable pageable);
}
