package com.skillshare.platform.controller;

import com.skillshare.platform.model.Post;
import com.skillshare.platform.repository.PostRepository;
import com.skillshare.platform.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/posts/{postId}/media")
@RequiredArgsConstructor
public class PostMediaController {
    private final PostRepository postRepo;
    private final FileStorageService storageService;

    @PostMapping
    public ResponseEntity<Post> uploadMedia(
            @PathVariable Long postId,
            @RequestParam("files") List<MultipartFile> files,
            @AuthenticationPrincipal OAuth2User principal) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // verify owner
        String oauthId = principal.getAttribute("sub");
        Assert.isTrue(post.getUser().getOauthId().equals(oauthId),
                "You are not the owner of this post");

        // limit to 3 files
        if (files.size() > 3) {
            return ResponseEntity.badRequest()
                    .body(null);
        }

        // store and assign URLs
        String[] urls = new String[3];
        for (int i = 0; i < files.size(); i++) {
            urls[i] = storageService.storeFile(files.get(i));
        }
        post.setMediaUrl1(urls[0]);
        post.setMediaUrl2(urls[1]);
        post.setMediaUrl3(urls[2]);

        postRepo.save(post);
        return ResponseEntity.ok(post);
    }
}
