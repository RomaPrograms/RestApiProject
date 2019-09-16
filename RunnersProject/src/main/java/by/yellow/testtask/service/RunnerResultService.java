package by.yellow.testtask.service;

import by.yellow.testtask.exception.PersistentException;
import by.yellow.testtask.model.entity.RunnerResult;

import java.util.List;
import java.util.Optional;

public interface RunnerResultService extends Service {
    Optional<List<RunnerResult>> getReport(int year, int id) throws PersistentException;
}
