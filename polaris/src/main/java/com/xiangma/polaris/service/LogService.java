package com.xiangma.polaris.service;

import com.xiangma.polaris.domain.*;
import com.xiangma.polaris.exception.IllegalLogingException;
import com.xiangma.polaris.exception.LogTooMuchException;
import com.xiangma.polaris.exception.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;
    private final TaskRepository taskRepository;

    public List<Log> getLogsByTask(Long taskId){
        Optional<Task> target = taskRepository.findById(taskId);
        if(target.isEmpty()){
            return Collections.emptyList();
        }
        return logRepository.findByTask(target.get());
    }

    public void deleteLog(Long logId){
        Optional<Log> target = logRepository.findById(logId);
        if(target.isEmpty()){
            return;
        }
        logRepository.delete(target.get());
    }

    public Log recordLog(Long taskId, LocalDate logDate, Long usedHour, Long progress, String memo) throws IllegalLogingException {
        Optional<Task> associatedTask = taskRepository.findById(taskId);
        if(associatedTask.isEmpty()){
            throw new TaskNotFoundException("task not found, check task id");
        }
        long currentProgress = logRepository
                .findByTask(associatedTask.get())
                .stream()
                .mapToLong(l -> l.getProgress())
                .sum();
        if(currentProgress + progress > 100){
            throw new LogTooMuchException("progress exceeds 100");
        }
        Log log = new Log();
        log.setLogDate(logDate);
        log.setProgress(progress);
        log.setUsedHour(usedHour);
        log.setTask(associatedTask.get());
        log.setMemo(memo);
        logRepository.save(log);
        return log;
    }

    public List<Log> getLogsByUserId(Long userId){
        List<Task> relatedTasks = taskRepository.findByAssigneeId(userId);
        return logRepository.findByTaskIn(relatedTasks);
    }
}