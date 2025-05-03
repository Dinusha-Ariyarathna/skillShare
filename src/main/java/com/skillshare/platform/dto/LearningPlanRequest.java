package com.skillshare.platform.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LearningPlanRequest {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
