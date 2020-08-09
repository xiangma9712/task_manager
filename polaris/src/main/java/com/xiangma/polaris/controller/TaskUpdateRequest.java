package com.xiangma.polaris.controller;

import com.xiangma.polaris.domain.TaskType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskUpdateRequest {
    private Long id;

    private String title;

    private String taskType;

    private LocalDate deadline;

    private String description;

    private Long assigneeId;

    private boolean done;

    public TaskType getConvertedTaskType(){
        return TaskType.valueOf(this.taskType);
    }
}
