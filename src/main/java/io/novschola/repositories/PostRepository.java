package io.novschola.repositories;

import io.novschola.model.Post;
import io.novschola.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository  extends PagingAndSortingRepository<Post, Long> {
    Iterable<Post> findAllByTitleLike(String title, Pageable pageable);
    Iterable<Post> findAllByContentLike(String content, Pageable pageable);
    Iterable<Post> findAllByAuthor(User author, Pageable pageable);
    Iterable<Post> findAllByAuthor_Id(Long authorId, Pageable pageable);
}
