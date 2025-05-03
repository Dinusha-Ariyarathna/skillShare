package com.skillshare.platform.controller;

import com.skillshare.platform.model.Comment;
import com.skillshare.platform.service.CommentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}")
    public Comment addComment(@PathVariable Long postId,
                              @RequestBody Map<String, String> request,
                              @AuthenticationPrincipal OAuth2User principal) {
        return commentService.addComment(postId, request.get("content"), principal);
    }

    @GetMapping("/{postId}")
    public List<Comment> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }

    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable Long commentId,
                                @AuthenticationPrincipal OAuth2User principal) {
        commentService.deleteComment(commentId, principal);
        return "Comment deleted.";
    }
}
