package io.novschola.repositories;

import io.novschola.model.Post;
import io.novschola.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository  extends PagingAndSortingRepository<Post, Long> {
    Page<Post> findAllByTitleLike(String title, Pageable pageable);
    Page<Post> findAllByContentLike(String content, Pageable pageable);
    Page<Post> findAllByAuthor(User author, Pageable pageable);
    Page<Post> findAllByAuthor_Id(Long authorId, Pageable pageable);
}
