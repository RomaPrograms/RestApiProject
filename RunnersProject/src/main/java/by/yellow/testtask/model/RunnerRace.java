package by.yellow.testtask.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "runners_races")
public class RunnerRace extends BaseEntity {
    @Column(name = "distance")
    private double distance;
    @Column(name = "race_time")
    private LocalTime raceTime;
    @Column(name = "race_date")
    private LocalDate raceDate;
    @Column(name = "speed")
    private double speed;
    @Column(name = "race_duration")
    private long raceDurationTimeInNano;
    @Column(name = "runner_id")
    private int runnerId;

    public RunnerRace() {
    }

    public RunnerRace(@JsonProperty("distance") double distance,
                      @JsonProperty("raceTime") LocalTime raceTime,
                      @JsonProperty("raceDate") LocalDate raceDate,
                      @JsonProperty("raceDurationTime") String raceDurationTime,
                      @JsonProperty("runnerId") int runnerId) {
        this.distance = distance;
        this.raceDate = raceDate;
        this.raceDurationTimeInNano
                = LocalTime.parse(raceDurationTime).toNanoOfDay();
        this.speed = distance/this.raceDurationTimeInNano * 1000000000;
        this.runnerId = runnerId;
        this.raceTime = raceTime;
    }
}
