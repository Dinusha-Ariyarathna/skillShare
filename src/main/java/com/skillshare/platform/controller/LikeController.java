package com.skillshare.platform.controller;

import com.skillshare.platform.model.User;
import com.skillshare.platform.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/posts/{postId}/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<Void> like(@PathVariable Long postId,
                                     @AuthenticationPrincipal OAuth2User principal) {
        likeService.likePost(postId, principal);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unlike(@PathVariable Long postId,
                                       @AuthenticationPrincipal OAuth2User principal) {
        likeService.unlikePost(postId, principal);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Map<String,Object>> getLikes(@PathVariable Long postId) {
        long count = likeService.countLikes(postId);
        List<User> users = likeService.getUsersWhoLiked(postId);
        // You might map User → a lighter DTO (id, name, profileImage)
        var userSummaries = users.stream()
                .map(u -> Map.of(
                        "id", u.getId(),
                        "name", u.getName(),
                        "profileImage", u.getProfileImage()))
                .toList();

        return ResponseEntity.ok(Map.of(
                "count", count,
                "users", userSummaries
        ));
    }
}
