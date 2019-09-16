package by.yellow.testtask.service;

import by.yellow.testtask.exception.PersistentException;
import by.yellow.testtask.model.entity.RunnerRace;

public interface RunnerRaceService extends Service {
    void updateRunnerRace(RunnerRace runnerRace) throws PersistentException;
}
