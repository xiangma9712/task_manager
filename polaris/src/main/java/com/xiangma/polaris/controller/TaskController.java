package com.xiangma.polaris.controller;

import com.xiangma.polaris.domain.Task;
import com.xiangma.polaris.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/{userId}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long userId){
        List<Task> tasks = taskService.findAll(userId);
        TaskResponse taskResponse = TaskResponse.builder()
                .tasks(tasks)
                .build();
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<HttpStatus> createNewTask(@RequestBody TaskAddRequest request){
        taskService.saveNewTask(
                request.getTitle(),
                request.getConvertedTaskType(),
                request.getDescription(),
                request.getConvertedDeadline(),
                request.getAssigneeId()
        );
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<HttpStatus> updateTask(@RequestBody TaskUpdateRequest request){
        taskService.updateTask(
                request.getId(),
                request.getTitle(),
                request.getConvertedTaskType(),
                request.getDescription(),
                request.getConvertedDeadline(),
                request.getAssigneeId(),
                request.isDoing(),
                request.isDone()
        );
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable Long taskId){
        taskService.delete(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/each/{taskId}")
    public ResponseEntity<Task> getOneTask(@PathVariable Long taskId){
        Task task = taskService.getTaskById(taskId);
        if(task != null){
            return new ResponseEntity<>(task,HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }
}
