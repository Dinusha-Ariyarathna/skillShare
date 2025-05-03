package com.skillshare.platform.dto;

import com.skillshare.platform.model.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String mediaUrl1;
    private String mediaUrl2;
    private String mediaUrl3;
    private LocalDateTime createdAt;

    public PostResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.mediaUrl1 = post.getMediaUrl1();
        this.mediaUrl2 = post.getMediaUrl2();
        this.mediaUrl3 = post.getMediaUrl3();
        this.createdAt = post.getCreatedAt();
    }
}
