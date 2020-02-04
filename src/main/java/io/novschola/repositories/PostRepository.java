package io.novschola.repositories;

import io.novschola.model.Post;
import io.novschola.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository  extends PagingAndSortingRepository<Post, Long> {
    Page<Post> findAllByContentLikeOrContentLike(String query, Pageable pageable);
    Page<Post> findAllByAuthor(User author, Pageable pageable);
    Page<Post> findAllByAuthor_Id(Long authorId, Pageable pageable);
}
