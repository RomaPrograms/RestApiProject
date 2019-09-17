package by.yellow.testtask.repository;

import by.yellow.testtask.model.entity.RunnerRace;
import by.yellow.testtask.model.entity.RunnerResult;

import java.util.List;
import java.util.Optional;

public interface RunnerRaceRepositoryCustom {
    boolean updateRunnerRace(RunnerRace runnerRace);
    Optional<List<RunnerResult>> getReport(int year, int runnerId);
}
