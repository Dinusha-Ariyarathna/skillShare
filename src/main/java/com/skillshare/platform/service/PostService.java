package com.skillshare.platform.service;
import com.skillshare.platform.dto.PostDTO;
import com.skillshare.platform.model.Post;
import com.skillshare.platform.model.User;
import com.skillshare.platform.repository.PostRepository;
import com.skillshare.platform.repository.UserRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
/**
 * @AUTHOR : Dinusha Ariyarathna
 * @DATE : 5/2/2025
 * @PROJECT : platform
 */
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(PostDTO postDTO, OAuth2User principal) {
        String oauthId = principal.getAttribute("sub");
        User user = userRepository.findByOauthId(oauthId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = Post.builder()
                .title(postDTO.getTitle())
                .description(postDTO.getDescription())
                .mediaUrl1(postDTO.getMediaUrl1())
                .mediaUrl2(postDTO.getMediaUrl2())
                .mediaUrl3(postDTO.getMediaUrl3())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post updatePost(Long postId, PostDTO postDTO, OAuth2User principal) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        String oauthId = principal.getAttribute("sub");
        if (!post.getUser().getOauthId().equals(oauthId)) {
            throw new RuntimeException("You are not the owner of this post.");
        }

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setMediaUrl1(postDTO.getMediaUrl1());
        post.setMediaUrl2(postDTO.getMediaUrl2());
        post.setMediaUrl3(postDTO.getMediaUrl3());

        return postRepository.save(post);
    }

    public void deletePost(Long postId, OAuth2User principal) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        String oauthId = principal.getAttribute("sub");
        if (!post.getUser().getOauthId().equals(oauthId)) {
            throw new RuntimeException("You are not the owner of this post.");
        }

        postRepository.delete(post);
    }

}
