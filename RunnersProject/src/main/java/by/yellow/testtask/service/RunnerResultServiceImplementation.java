package by.yellow.testtask.service;

import by.yellow.testtask.exception.PersistentException;
import by.yellow.testtask.model.dao.RunnerResultDao;
import by.yellow.testtask.model.entity.RunnerResult;

import java.util.List;
import java.util.Optional;

public class RunnerResultServiceImplementation extends RaceServiceRealization
        implements RunnerResultService {

    @Override
    public Optional<List<RunnerResult>> getReport(int year, int id) throws PersistentException {
        RunnerResultDao runnerResultDao = transaction.createDao(RunnerResultDao.class);
        return runnerResultDao.getReport(year, id);
    }
}
