package com.xiangma.polaris.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "log")
@NoArgsConstructor
public class Log {
    /**　自動伝搬ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 紐づくタスク */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Task task;

    /** 作業日時 */
    @Column(nullable = false)
    private LocalDate logDate;

    /** 作業時間 */
    @Column(nullable = false)
    private Long usedHour;

    /** 進捗 */
    @Column(nullable = false)
    private Long progress;

    /** メモ */
    private String memo;
}
