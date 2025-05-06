package com.skillshare.platform.controller;

import com.skillshare.platform.dto.PostDTO;
import com.skillshare.platform.model.Post;
import com.skillshare.platform.service.PostService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @AUTHOR : Dinusha Ariyarathna
 * @DATE : 5/2/2025
 * @PROJECT : platform
 */

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public Post createPost(@RequestBody PostDTO postDTO,
                           @AuthenticationPrincipal OAuth2User principal) {
        return postService.createPost(postDTO, principal);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PutMapping("/{postId}")
    public Post updatePost(@PathVariable Long postId,
                           @RequestBody PostDTO postDTO,
                           @AuthenticationPrincipal OAuth2User principal) {
        return postService.updatePost(postId, postDTO, principal);
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Long postId,
                             @AuthenticationPrincipal OAuth2User principal) {
        postService.deletePost(postId, principal);
        return "Post deleted successfully!.";
    }


}
