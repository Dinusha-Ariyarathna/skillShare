package com.skillshare.platform.controller;

import com.skillshare.platform.model.Post;
import com.skillshare.platform.model.Tag;
import com.skillshare.platform.service.TagService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("/tags")
    public List<Tag> listTags() {
        return tagService.listTags();
    }

    @PostMapping("/tags")
    public Tag createTag(@RequestBody TagRequest request) {
        return tagService.createTag(request.getName());
    }

    @PostMapping("/posts/{postId}/tags")
    public Set<Tag> assignTags(@PathVariable Long postId,
                               @RequestBody TagListRequest request,
                               @AuthenticationPrincipal OAuth2User principal) {
        return tagService.assignTagsToPost(postId, request.getTags(), principal);
    }

    @GetMapping("/tags/{tagName}/posts")
    public List<Post> postsByTag(@PathVariable String tagName) {
        return tagService.getPostsByTag(tagName);
    }

    @Data
    public static class TagRequest {
        private String name;
    }

    @Data
    public static class TagListRequest {
        private List<String> tags;
    }
}
