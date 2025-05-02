package com.skillshare.platform.controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @AUTHOR : Dinusha Ariyarathna
 * @DATE : 5/2/2025
 * @PROJECT : platform
 */


@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "<h1>Welcome to Skill Sharing Platform</h1><a href='/oauth2/authorization/google'>Login with Google</a>";
    }

    @GetMapping("/profile")
    public String userProfile(@AuthenticationPrincipal OAuth2User principal) {
        return "Hello, " + principal.getAttribute("name") + "<br>Email: " + principal.getAttribute("email");
    }
}
