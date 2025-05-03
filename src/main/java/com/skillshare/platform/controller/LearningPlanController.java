package com.skillshare.platform.controller;

import com.skillshare.platform.dto.LearningPlanRequest;
import com.skillshare.platform.dto.LearningPlanResponse;
import com.skillshare.platform.model.LearningPlan;
import com.skillshare.platform.service.LearningPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class LearningPlanController {

    private final LearningPlanService svc;

    @PostMapping
    public LearningPlanResponse create(
            @RequestBody LearningPlanRequest req,
            @AuthenticationPrincipal OAuth2User principal) {
        LearningPlan lp = svc.createPlan(req, principal);
        return new LearningPlanResponse(lp);
    }

    @GetMapping
    public List<LearningPlanResponse> list(
            @AuthenticationPrincipal OAuth2User principal) {
        return svc.getMyPlans(principal).stream()
                .map(LearningPlanResponse::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public LearningPlanResponse update(
            @PathVariable Long id,
            @RequestBody LearningPlanRequest req,
            @AuthenticationPrincipal OAuth2User principal) {
        return new LearningPlanResponse(svc.updatePlan(id, req, principal));
    }

    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable Long id,
            @AuthenticationPrincipal OAuth2User principal) {
        svc.deletePlan(id, principal);
        return "Deleted";
    }

    @GetMapping("/{id}")
    public LearningPlanResponse getById(
            @PathVariable Long id,
            @AuthenticationPrincipal OAuth2User principal) {

        // Ensure the plan belongs to the user
        LearningPlan lp = svc.getMyPlans(principal).stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Plan not found or not yours"));

        return new LearningPlanResponse(lp);
    }
}
