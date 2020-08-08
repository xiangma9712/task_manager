package com.xiangma.polaris.controller;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LogAddRequest {
    /** 紐づけるタスクのid */
    private long taskId;

    /** 作業日時 */
    private LocalDate logDate;

    /** 作業時間 */
    private Long usedHour;

    /** 進捗 */
    private Long progress;

    /** メモ */
    private String memo;
}
