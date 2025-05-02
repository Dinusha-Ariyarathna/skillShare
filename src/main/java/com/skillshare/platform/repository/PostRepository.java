package com.skillshare.platform.repository;

import com.skillshare.platform.model.Post;
import com.skillshare.platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @AUTHOR : Dinusha Ariyarathna
 * @DATE : 5/2/2025
 * @PROJECT : platform
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
}
