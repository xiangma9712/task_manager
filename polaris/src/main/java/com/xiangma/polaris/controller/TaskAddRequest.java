package com.xiangma.polaris.controller;

import com.xiangma.polaris.domain.TaskType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskAddRequest {
    private String title;

    private String taskType;

    private String deadline;

    private String description;

    private Long assigneeId;

    public TaskType getConvertedTaskType(){
        return TaskType.valueOf(this.taskType);
    }

    public LocalDate getConvertedDeadline(){
        return LocalDate.parse(this.deadline);
    }
}
