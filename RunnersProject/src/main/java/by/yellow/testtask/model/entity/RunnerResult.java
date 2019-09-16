package by.yellow.testtask.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class RunnerResult extends BaseEntity {
    private int week;
    private LocalDate startWeek;
    private LocalDate endWeek;
    private double distance;
    private double speed;
    private LocalTime raceDuration;

    public RunnerResult() {
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public LocalDate getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(LocalDate startWeek) {
        this.startWeek = startWeek;
    }

    public LocalDate getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(LocalDate endWeek) {
        this.endWeek = endWeek;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public LocalTime getRaceDuration() {
        return raceDuration;
    }

    public void setRaceDuration(LocalTime raceDuration) {
        this.raceDuration = raceDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RunnerResult)) return false;
        if (!super.equals(o)) return false;
        RunnerResult that = (RunnerResult) o;
        return getWeek() == that.getWeek() &&
                Double.compare(that.getDistance(), getDistance()) == 0 &&
                Double.compare(that.getSpeed(), getSpeed()) == 0 &&
                Objects.equals(getStartWeek(), that.getStartWeek()) &&
                Objects.equals(getEndWeek(), that.getEndWeek()) &&
                Objects.equals(getRaceDuration(), that.getRaceDuration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getWeek(), getStartWeek(),
                getEndWeek(), getDistance(), getSpeed(), getRaceDuration());
    }
}
