package by.yellow.testtask.model.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class RunnerResult extends BaseEntity {
    private int week;
    private LocalDate startWeek;
    private LocalDate endWeek;
    private double distance;
    private double speed;
    private long raceDuration;
}
