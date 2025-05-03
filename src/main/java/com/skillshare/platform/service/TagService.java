package com.skillshare.platform.service;

import com.skillshare.platform.model.Post;
import com.skillshare.platform.model.Tag;
import com.skillshare.platform.repository.PostRepository;
import com.skillshare.platform.repository.TagRepository;
import com.skillshare.platform.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    public Tag createTag(String name) {
        return tagRepo.findByName(name)
                .orElseGet(() -> tagRepo.save(Tag.builder().name(name).build()));
    }

    public List<Tag> listTags() {
        return tagRepo.findAll();
    }

    @Transactional
    public Set<Tag> assignTagsToPost(Long postId, List<String> tagNames, OAuth2User principal) {
        // verify post exists and owner
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        String oauthId = principal.getAttribute("sub");
        if (!post.getUser().getOauthId().equals(oauthId)) {
            throw new RuntimeException("Not the owner");
        }

        // create/fetch tags
        Set<Tag> tags = new HashSet<>();
        for (String name : tagNames) {
            tags.add(createTag(name.trim()));
        }

        post.getTags().clear();
        post.getTags().addAll(tags);
        postRepo.save(post);

        return post.getTags();
    }

    public List<Post> getPostsByTag(String tagName) {
        Tag tag = tagRepo.findByName(tagName)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        return new ArrayList<>(tag.getPosts());
    }
}
