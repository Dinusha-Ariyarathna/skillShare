package com.skillshare.platform.service;

import com.skillshare.platform.model.Comment;
import com.skillshare.platform.model.Post;
import com.skillshare.platform.model.User;
import com.skillshare.platform.repository.CommentRepository;
import com.skillshare.platform.repository.PostRepository;
import com.skillshare.platform.repository.UserRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Comment addComment(Long postId, String content, OAuth2User principal) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        String oauthId = principal.getAttribute("sub");
        User user = userRepository.findByOauthId(oauthId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    public List<Comment> getComments(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId);
    }

    public void deleteComment(Long commentId, OAuth2User principal) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        String oauthId = principal.getAttribute("sub");
        if (!comment.getUser().getOauthId().equals(oauthId)) {
            throw new RuntimeException("Unauthorized to delete this comment.");
        }

        commentRepository.delete(comment);
    }
}
