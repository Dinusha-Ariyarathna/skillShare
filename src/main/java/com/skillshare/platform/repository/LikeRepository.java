package com.skillshare.platform.repository;

import com.skillshare.platform.model.Like;
import com.skillshare.platform.model.Post;
import com.skillshare.platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
    long countByPost(Post post);
    List<Like> findByPost(Post post);
    void deleteByUserAndPost(User user, Post post);
}
