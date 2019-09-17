package by.yellow.testtask.RunnersProject;

import by.yellow.testtask.exception.PersistentException;
import by.yellow.testtask.model.entity.RunnerRace;
import by.yellow.testtask.model.mysql.TransactionFactoryRealization;
import by.yellow.testtask.repository.RunnerRaceRepository;
import by.yellow.testtask.rest.RunnerRaceController;
import by.yellow.testtask.service.RunnerRaceService;
import by.yellow.testtask.service.RunnerResultService;
import by.yellow.testtask.service.ServiceFactoryRealization;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RunnerRaceControllerTests {
    @Mock
    private RunnerRaceRepository runnerRaceRepository;

    @InjectMocks
    private RunnerRaceController runnerRaceController;

    private static RunnerRace runnerRace;

    private static ServiceFactoryRealization serviceRealization;

    @Mock
    private static RunnerRaceService runnerService;

    @Mock
    private static RunnerResultService runnerResultService;

    @BeforeClass
    public static void setUp() {
        try {
            serviceRealization = new ServiceFactoryRealization(
                    new TransactionFactoryRealization());
            runnerService
                    = serviceRealization.getService(RunnerRaceService.class);
            runnerResultService
                    = serviceRealization.getService(RunnerResultService.class);
        } catch (PersistentException e) {
            e.printStackTrace();
        }
        runnerRace = new RunnerRace();
        runnerRace.setId(1);
        runnerRace.setDistance(1000);
        runnerRace.setRaceDate(LocalDate.parse("2019-08-08"));
        runnerRace.setRaceTime(LocalTime.parse("20:20:20"));
        runnerRace.setRaceDurationTimeInNano(102354568);
        runnerRace.setSpeed(25);
        runnerRace.setRunnerId(1);
    }

    @Test
    public void testAddRace() {
        runnerRaceController.addRace(runnerRace);
        verify(runnerRaceRepository).save(runnerRace);
    }

    @Test
    public void testGetRaceById() {
        when(runnerRaceRepository.findById(1))
                .thenReturn(Optional.of(runnerRace));
        Optional<RunnerRace> actualRunnerRace
                = runnerRaceRepository.findById(1);
        runnerRaceController.getRace(1);
        verify(runnerRaceRepository, times(2)).findById(1);
        Assert.assertEquals(actualRunnerRace, Optional.of(runnerRace));
    }

    @Test
    public void testGetAllRaceById() {
        List<RunnerRace> runnerRaces = new ArrayList<>();
        when(runnerRaceRepository.getAllRaceByRunnerId(1))
                .thenReturn(Optional.of((runnerRaces)));
        Optional<List<RunnerRace>> actualRunnerRace
                = runnerRaceRepository.getAllRaceByRunnerId(1);
        runnerRaceController.getAllRace(1);
        verify(runnerRaceRepository, times(2))
                .getAllRaceByRunnerId(1);
        Assert.assertEquals(actualRunnerRace, Optional.of(runnerRaces));
    }

    @Test
    public void testDeleteRace() {
        runnerRaceController.deleteResult(1);
        verify(runnerRaceRepository).deleteById(1);
    }
}
