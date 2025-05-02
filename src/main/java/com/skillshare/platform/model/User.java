package com.skillshare.platform.model;
import jakarta.persistence.*;
import lombok.*;
/**
 * @AUTHOR : Dinusha Ariyarathna
 * @DATE : 5/2/2025
 * @PROJECT : platform
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oauthId;  // Google sub ID
    private String name;
    private String email;
    private String profileImage;

    private String provider; // e.g., "GOOGLE"
}
