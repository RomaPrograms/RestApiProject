package by.yellow.testtask.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class RunnerResult {
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
}
