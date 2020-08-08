package com.xiangma.polaris.controller;

import com.xiangma.polaris.domain.Log;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class LogResponse {
    private List<Log> logs;
    private long sumProgress;
}
