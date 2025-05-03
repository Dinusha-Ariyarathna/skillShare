package com.skillshare.platform.controller;


import com.skillshare.platform.dto.PostResponseDTO;
import com.skillshare.platform.dto.UserProfileDTO;
import com.skillshare.platform.model.Post;
import com.skillshare.platform.model.User;
import com.skillshare.platform.repository.PostRepository;
import com.skillshare.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ProfileController {

    private final UserRepository userRepo;
    private final PostRepository postRepo;

    // 1) Get logged-in user's posts
    @GetMapping("/me/posts")
    public List<PostResponseDTO> getMyPosts(@AuthenticationPrincipal OAuth2User principal) {
        String oauthId = principal.getAttribute("sub");
        User user = userRepo.findByOauthId(oauthId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Post> posts = postRepo.findByUser(user);
        return posts.stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());
    }

    // 2) Get any user's profile by ID (info + their posts)
    @GetMapping("/{userId}/profile")
    public UserProfileDTO getUserProfile(@PathVariable Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<PostResponseDTO> posts = postRepo.findByUser(user)
                .stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());

        return new UserProfileDTO(user, posts);
    }
}
