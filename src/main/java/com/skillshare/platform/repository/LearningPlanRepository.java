package com.skillshare.platform.repository;

import com.skillshare.platform.model.LearningPlan;
import com.skillshare.platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LearningPlanRepository extends JpaRepository<LearningPlan, Long> {
    List<LearningPlan> findByUser(User user);
}
