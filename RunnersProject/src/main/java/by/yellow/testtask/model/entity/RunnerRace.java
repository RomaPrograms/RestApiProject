package by.yellow.testtask.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
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
    private LocalTime raceDurationTime;
    @Column(name = "runner_id")
    private int runnerId;

    public RunnerRace() {
    }

    public RunnerRace(@JsonProperty("distance") double distance,
                      @JsonProperty("raceTime") LocalTime raceTime,
                      @JsonProperty("raceDate") LocalDate raceDate,
                      @JsonProperty("speed") double speed,
                      @JsonProperty("raceDurationTime") String raceDurationTime,
                      @JsonProperty("runnerId") int runnerId) {
        this.distance = distance;
        this.raceDate = raceDate;
        this.raceDurationTime = LocalTime.parse(raceDurationTime);
        this.speed = speed;
        this.runnerId = runnerId;
        this.raceTime = raceTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public LocalTime getRaceTime() {
        return raceTime;
    }

    public void setRaceTime(LocalTime raceTime) {
        this.raceTime = raceTime;
    }

    public LocalDate getRaceDate() {
        return raceDate;
    }

    public void setRaceDate(LocalDate raceDate) {
        this.raceDate = raceDate;
    }

    public void setRaceDurationTime(LocalTime raceDurationTime) {
        this.raceDurationTime = raceDurationTime;
    }

    public LocalTime getRaceDurationTime() {
        return raceDurationTime;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(int runnerId) {
        this.runnerId = runnerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RunnerRace)) return false;
        if (!super.equals(o)) return false;
        RunnerRace that = (RunnerRace) o;
        return Double.compare(that.getDistance(), getDistance()) == 0 &&
                Double.compare(that.getSpeed(), getSpeed()) == 0 &&
                getRunnerId() == that.getRunnerId() &&
                Objects.equals(getRaceTime(), that.getRaceTime()) &&
                Objects.equals(getRaceDate(), that.getRaceDate()) &&
                Objects.equals(getRaceDurationTime(), that.getRaceDurationTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDistance(), getRaceTime(),
                getRaceDate(), getSpeed(), getRaceDurationTime(),
                getRunnerId());
    }
}
