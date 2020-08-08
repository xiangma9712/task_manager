package com.xiangma.polaris.controller;

import com.xiangma.polaris.domain.Task;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TaskResponse {
    private List<Task> tasks;
}
