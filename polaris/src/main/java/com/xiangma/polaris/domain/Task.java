package com.xiangma.polaris.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "task")
public class Task {
    /**　自動伝搬ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** タイトル */
    @Column(nullable = false)
    private String title;

    /** タスクの分類 */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    /** 説明 */
    @Column(nullable = false)
    private String description;

    /** 期限 */
    private LocalDate deadline;

    /** タスク保持者 */
    @Column(nullable = false)
    private Long assigneeId;

    /** 終了flag */
    @Column(nullable = false)
    private boolean done;

    public static Task getNewTemplate(){
        Task newOne = new Task();
        newOne.setDone(false);
        return newOne;
    }
}
