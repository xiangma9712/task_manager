package com.xiangma.polaris.service;

import com.xiangma.polaris.domain.*;
import com.xiangma.polaris.exception.IllegalLogingException;
import com.xiangma.polaris.exception.LogTooMuchException;
import com.xiangma.polaris.exception.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        if(target.get().getTask().isDone()){
            Task redo = target.get().getTask();
            redo.setDone(false);
            taskRepository.save(redo);
        }
        logRepository.delete(target.get());

    }

    public Log recordLog(Long taskId, LocalDate logDate, Long usedHour, Long progress, String memo) throws IllegalLogingException {
        Optional<Task> associatedTask = taskRepository.findById(taskId);
        if(associatedTask.isEmpty()){
            throw new TaskNotFoundException("task not found, check task id");
        }
        Task task = associatedTask.get();
        long currentProgress = logRepository
                .findByTask(task)
                .stream()
                .mapToLong(l -> l.getProgress())
                .sum();
        if(currentProgress + progress > 100L){
            throw new LogTooMuchException("progress exceeds 100");
        }
        if(currentProgress + progress == 100L){
            task.setDone(true);
            taskRepository.save(task);
        }
        Log log = new Log();
        log.setLogDate(logDate);
        log.setProgress(progress);
        log.setUsedHour(usedHour);
        log.setTask(task);
        log.setMemo(memo);
        logRepository.save(log);
        return log;
    }

    public List<Log> getLogsByUserId(Long userId){
        List<Task> relatedTasks = taskRepository.findByAssigneeId(userId);
        return logRepository.findByTaskIn(relatedTasks);
    }

    public Map<String,Long> getLogHistory(Long userId){
        List<Log> logs = getLogsByUserId(userId);
        List<Log> lastOneYearLog = logs
                .stream()
                .filter(l -> Period.between(l.getLogDate(),LocalDate.now()).getYears() < 1)
                .collect(Collectors.toList());

        Set<String> month = lastOneYearLog
                .stream()
                .map(Log::getLogDate)
                .map(d -> d.format(DateTimeFormatter.ofPattern("LLL")))
                .collect(Collectors.toSet());

        return month.stream().collect(Collectors.toMap(
                m -> m,
                m -> lastOneYearLog
                        .stream()
                        .filter(l -> l.getLogDate().format(DateTimeFormatter.ofPattern("LLL")).equals(m))
                        .mapToLong(Log::getUsedHour)
                        .sum(),
                (oldVal,newVal) -> newVal,
                HashMap::new
        ));
    }
}
