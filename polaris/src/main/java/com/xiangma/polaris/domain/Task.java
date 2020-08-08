package com.xiangma.polaris.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    /** 実行中flag */
    @Column(nullable = false)
    private boolean doing;

    /** 終了flag */
    @Column(nullable = false)
    private boolean done;

    public static Task getNewTemplate(){
        Task newOne = new Task();
        newOne.setDoing(false);
        newOne.setDone(false);
        return newOne;
    }
}
