package by.yellow.testtask.service;


import by.yellow.testtask.exception.PersistentException;
import by.yellow.testtask.model.dao.RunnerRaceDao;
import by.yellow.testtask.model.entity.RunnerRace;
import org.springframework.stereotype.Service;

@Service
public class RunnerRaceServiceImplementation extends RaceServiceRealization
        implements RunnerRaceService {

    @Override
    public void updateRunnerRace(RunnerRace runnerRace) throws PersistentException {
        RunnerRaceDao runnerRaceDao = transaction.createDao(RunnerRaceDao.class);
        runnerRaceDao.updateRunnerRace(runnerRace);
    }
}
