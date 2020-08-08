package com.xiangma.polaris.service;

import com.xiangma.polaris.domain.Task;
import com.xiangma.polaris.domain.TaskRepository;
import com.xiangma.polaris.domain.TaskType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> findAll(Long assignerId){
        return taskRepository.findByAssigneeId(assignerId);
    }

    public Task saveNewTask(String title, TaskType taskType, String description, LocalDate deadline, Long assignerId){
        Task task = Task.getNewTemplate();
        task.setTitle(title);
        task.setTaskType(taskType);
        task.setDescription(description);
        task.setDeadline(deadline);
        task.setAssigneeId(assignerId);
        taskRepository.save(task);
        return task;
    }

    public void delete(Long task_id){
        Optional<Task> target = taskRepository.findById(task_id);
        if(target.isEmpty()){
            return;
        }
        taskRepository.delete(target.get());
    }

    public Task initializeTask(Long task_id){
        Optional<Task> target = taskRepository.findById(task_id);
        if(target.isEmpty()){
            return null;
        }
        Task task = target.get();
        task.setDoing(false);
        task.setDone(false);
        taskRepository.save(task);
        return task;
    }

    public Task startTask(Long task_id){
        Optional<Task> target = taskRepository.findById(task_id);
        if(target.isEmpty()){
            return null;
        }
        Task task = target.get();
        task.setDoing(true);
        task.setDone(false);
        taskRepository.save(task);
        return task;
    }

    public void endTask(Long task_id) {
        Optional<Task> target = taskRepository.findById(task_id);
        if (target.isEmpty()) {
            return;
        }
        Task task = target.get();
        task.setDoing(false);
        task.setDone(true);
        taskRepository.save(task);
    }

    public void updateTask(
            Long task_id,
            String title,
            TaskType taskType,
            String description,
            LocalDate deadline,
            Long assignerId,
            boolean doing,
            boolean done){
        Optional<Task> target = taskRepository.findById(task_id);
        if(target.isEmpty()){
            return;
        }
        Task task = target.get();
        task.setTitle(title);
        task.setTaskType(taskType);
        task.setDescription(description);
        task.setDeadline(deadline);
        task.setAssigneeId(assignerId);
        task.setDoing(doing);
        task.setDone(done);
        taskRepository.save(task);
    }
}
