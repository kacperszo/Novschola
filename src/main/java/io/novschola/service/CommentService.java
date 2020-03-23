package io.novschola.service;

import io.novschola.exception.BadRequestException;
import io.novschola.model.Comment;
import io.novschola.model.Post;
import io.novschola.model.User;
import io.novschola.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment create(Comment comment) {
        comment.setId(null);
        comment.setCreationTime(null);
        return commentRepository.save(comment);
    }

    public Comment update(Comment comment) {
        return null;
    }

    public Comment findById(Long id) {
        return null;
    }

    public Comment findByAuthorId(Long id) {
        return null;
    }

    public Comment findByAuthor(User user) {
        return null;
    }

    public List<Comment> findAll() {
        return null;
    }

    public void delete(Comment comment) {

    }

    public void deleteById(Long id) {

    }

    public List<Comment> findAllByPost(Post post) {
        return null;
    }

    public List<Comment> findAllByPostId(Long id) {
        return null;
    }

    public List<Comment> findAllByAuthor(User author) {
        return null;
    }

    public List<Comment> findAllByAuthorId(Long id) {
        return null;
    }

}