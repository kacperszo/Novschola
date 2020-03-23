package io.novschola.service;

import io.novschola.exception.BadRequestException;
import io.novschola.exception.ItemNotFoundException;
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

        if (comment.getId() == null || !commentRepository.existsById(comment.getId())) {
            throw new BadRequestException();
        }
        return commentRepository.save(comment);
    }

    public Comment findById(Long id) {

        return commentRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public void delete(Comment comment) {

    }

    public void deleteById(Long id) {

    }

    public List<Comment> findAllByPost(Post post) {
        return commentRepository.findAllByPost(post);
    }

    public List<Comment> findAllByPostId(Long id) {
        return commentRepository.findAllByPostId(id);
    }

    public List<Comment> findAllByAuthor(User author) {
        return null;
    }

    public List<Comment> findAllByAuthorId(Long id) {
        return commentRepository.findAllByAuthorId(id);
    }

}
