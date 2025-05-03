package com.skillshare.platform.dto;

import com.skillshare.platform.model.User;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileDTO {
    private Long id;
    private String name;
    private String email;
    private String profileImage;
    private List<PostResponseDTO> posts;

    public UserProfileDTO(User user, List<PostResponseDTO> posts) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
        this.posts = posts;
    }
}
