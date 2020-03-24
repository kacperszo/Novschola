package io.novschola.api.v1.controller;

import io.novschola.api.v1.model.dto.request.CreatePostRequest;
import io.novschola.api.v1.model.dto.request.UpdatePostRequest;
import io.novschola.api.v1.model.dto.response.PostResponse;
import io.novschola.converters.PostToPostResponseConverter;
import io.novschola.exception.BadRequestException;
import io.novschola.exception.ForbiddenException;
import io.novschola.model.Post;
import io.novschola.repositories.RoleRepository;
import io.novschola.service.PostService;
import io.novschola.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Post rest controller
 *
 * @author Kacper Szot
 */
@RestController
@Slf4j
@RequestMapping("v1/posts")
public class PostController {

    private PostService postService;
    private PostToPostResponseConverter postToPostResponseConverter;
    private UserService userService;
    private RoleRepository roleRepository;

    public PostController(PostService postService, PostToPostResponseConverter postToPostResponseConverter, UserService userService, RoleRepository roleRepository) {
        this.postService = postService;
        this.postToPostResponseConverter = postToPostResponseConverter;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("")
    public Page<PostResponse> getPosts(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = Integer.MAX_VALUE + "") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return postService.findAll(PageRequest.of(pageNo, pageSize, Sort.by(sortBy))).map(s -> postToPostResponseConverter.convert(s));

    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postToPostResponseConverter.convert(postService.findById(id));
    }

    @GetMapping("search/{query}")
    public Page<PostResponse> searchPosts(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = Integer.MAX_VALUE + "") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @PathVariable String query) {

        return postService.search(query, PageRequest.of(pageNo, pageSize, Sort.by(sortBy))).map(s -> postToPostResponseConverter.convert(s));
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid CreatePostRequest request, Principal principal, BindingResult result) {

        if (result.hasErrors()) {
            throw new BadRequestException(result);
        }

        Post post = new Post();
        post.setAuthor(userService.findByEmail(principal.getName()));
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        return new ResponseEntity<>(postToPostResponseConverter.convert(postService.create(post)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PostAuthorize("returnObject.getAuthor().getEmail() == authentication.name OR hasRole('ROLE_ADMIN')")
    public PostResponse updatePost(@RequestBody @Valid UpdatePostRequest request, @PathVariable Long id, Principal principal, BindingResult result) {

        if (result.hasErrors()) {
            throw new BadRequestException(result);
        }

        Post post = postService.findById(id);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        return postToPostResponseConverter.convert(postService.update(post));
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id, Authentication authentication) {

        Post post = postService.findById(id);
        if (!(authentication.getName().equals(post.getAuthor().getEmail()) || (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))))) {
            throw new ForbiddenException("Forbidden");
        }

        postService.delete(post);

    }

}

