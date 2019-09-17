package by.yellow.testtask.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RunnerResult extends BaseEntity {
    private int week;
    private LocalDate startWeek;
    private LocalDate endWeek;
    private double distance;
    private double speed;
    private long raceDuration;
}
