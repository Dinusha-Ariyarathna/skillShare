package com.skillshare.platform.dto;

import com.skillshare.platform.model.LearningPlan;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LearningPlanResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LearningPlanResponse(LearningPlan lp) {
        this.id = lp.getId();
        this.title = lp.getTitle();
        this.description = lp.getDescription();
        this.startDate = lp.getStartDate();
        this.endDate = lp.getEndDate();
        this.createdAt = lp.getCreatedAt();
        this.updatedAt = lp.getUpdatedAt();
    }
}
