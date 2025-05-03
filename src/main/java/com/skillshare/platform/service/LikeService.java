package com.skillshare.platform.service;

import com.skillshare.platform.model.Like;
import com.skillshare.platform.model.Post;
import com.skillshare.platform.model.User;
import com.skillshare.platform.repository.LikeRepository;
import com.skillshare.platform.repository.PostRepository;
import com.skillshare.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    public void likePost(Long postId, OAuth2User principal) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        String oauthId = principal.getAttribute("sub");
        User user = userRepo.findByOauthId(oauthId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        likeRepo.findByUserAndPost(user, post)
                .orElseGet(() -> likeRepo.save(Like.builder()
                        .user(user)
                        .post(post)
                        .build()));
    }

    public void unlikePost(Long postId, OAuth2User principal) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        String oauthId = principal.getAttribute("sub");
        User user = userRepo.findByOauthId(oauthId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        likeRepo.deleteByUserAndPost(user, post);
    }

    public long countLikes(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return likeRepo.countByPost(post);
    }

    public List<User> getUsersWhoLiked(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return likeRepo.findByPost(post).stream()
                .map(Like::getUser)
                .toList();
    }
}
