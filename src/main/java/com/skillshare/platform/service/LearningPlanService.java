package com.skillshare.platform.service;

import com.skillshare.platform.dto.LearningPlanRequest;
import com.skillshare.platform.model.LearningPlan;
import com.skillshare.platform.model.User;
import com.skillshare.platform.repository.LearningPlanRepository;
import com.skillshare.platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LearningPlanService {

    private final LearningPlanRepository planRepo;
    private final UserRepository userRepo;

    public LearningPlan createPlan(LearningPlanRequest req, OAuth2User principal) {
        User user = userRepo.findByOauthId(principal.getAttribute("sub"))
                .orElseThrow(() -> new RuntimeException("User not found"));

        LearningPlan plan = LearningPlan.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .user(user)
                .build();

        return planRepo.save(plan);
    }

    public List<LearningPlan> getMyPlans(OAuth2User principal) {
        User user = userRepo.findByOauthId(principal.getAttribute("sub"))
                .orElseThrow(() -> new RuntimeException("User not found"));
        return planRepo.findByUser(user);
    }

    public LearningPlan updatePlan(Long id, LearningPlanRequest req, OAuth2User principal) {
        LearningPlan plan = planRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
        if (!plan.getUser().getOauthId().equals(principal.getAttribute("sub")))
            throw new RuntimeException("Not your plan");

        plan.setTitle(req.getTitle());
        plan.setDescription(req.getDescription());
        plan.setStartDate(req.getStartDate());
        plan.setEndDate(req.getEndDate());
        return planRepo.save(plan);
    }

    public void deletePlan(Long id, OAuth2User principal) {
        LearningPlan plan = planRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
        if (!plan.getUser().getOauthId().equals(principal.getAttribute("sub")))
            throw new RuntimeException("Not your plan");
        planRepo.delete(plan);
    }
}
