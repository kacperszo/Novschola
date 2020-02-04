package io.novschola.repositories;

import io.novschola.model.Post;
import io.novschola.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository  extends CrudRepository<Post, Long> {
    Iterable<Post> findAllByTitleLike(String title);
    Iterable<Post> findAllByContentLike(String content);
    Iterable<Post> findAllByAuthor(User author);
    Iterable<Post> findAllByAuthor_Id(Long authorId);
}
