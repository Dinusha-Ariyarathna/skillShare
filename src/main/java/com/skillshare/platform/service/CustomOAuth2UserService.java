package com.skillshare.platform.service;
import com.skillshare.platform.model.User;
import com.skillshare.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @AUTHOR : Dinusha Ariyarathna
 * @DATE : 5/2/2025
 * @PROJECT : platform
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService{
    private final UserRepository userRepository;
    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String oauthId = (String) attributes.get("sub");
        String name = (String) attributes.get("name");
        String email = (String) attributes.get("email");
        String picture = (String) attributes.get("picture");

        userRepository.findByOauthId(oauthId).orElseGet(() -> {
            User newUser = User.builder()
                    .oauthId(oauthId)
                    .name(name)
                    .email(email)
                    .profileImage(picture)
                    .provider("GOOGLE")
                    .build();
            return userRepository.save(newUser);
        });

        return oAuth2User; // you can wrap with a custom user object if needed
    }
}
