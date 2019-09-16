package by.yellow.testtask.model.dao;

import by.yellow.testtask.exception.PersistentException;
import by.yellow.testtask.model.entity.RunnerResult;

import java.util.List;
import java.util.Optional;

public interface RunnerResultDao extends Dao<RunnerResult>{
    Optional<List<RunnerResult>> getReport(int year, int id) throws PersistentException;
}
