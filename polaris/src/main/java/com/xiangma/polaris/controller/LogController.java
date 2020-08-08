package com.xiangma.polaris.controller;

import com.xiangma.polaris.domain.Log;
import com.xiangma.polaris.exception.IllegalLogingException;
import com.xiangma.polaris.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/log")
@RequiredArgsConstructor
public class LogController {
    private final LogService logService;

    @GetMapping("/{taskId}")
    public ResponseEntity<LogResponse> getLogs(@PathVariable Long taskId){
        List<Log> logs = logService.getLogsByTask(taskId);
        long sumProgress = logs.stream().mapToLong(l -> l.getProgress()).sum();
        LogResponse response = LogResponse.builder()
                .logs(logs)
                .sumProgress(sumProgress)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<HttpStatus> deleteLog(@PathVariable Long logId){
        logService.deleteLog(logId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/")
    public ResponseEntity<HttpStatus> recordLog(@RequestBody LogAddRequest request){
        try{
            logService.recordLog(
                    request.getTaskId(),
                    request.getLogDate(),
                    request.getUsedHour(),
                    request.getProgress(),
                    request.getMemo()
            );
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (IllegalLogingException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
