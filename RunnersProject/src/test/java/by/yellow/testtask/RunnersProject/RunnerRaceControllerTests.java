package by.yellow.testtask.RunnersProject;

import by.yellow.testtask.model.RunnerRace;
import by.yellow.testtask.model.RunnerResult;
import by.yellow.testtask.repository.RunnerRaceRepository;
import by.yellow.testtask.rest.RunnerRaceController;
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

    @BeforeClass
    public static void setUp() {
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
    public void testUpdateRaceById() {
        when(runnerRaceRepository.updateRunnerRace(runnerRace))
                .thenReturn(true);
        boolean actualResult
                = runnerRaceRepository.updateRunnerRace(runnerRace);
        runnerRaceController.updateRaceById(runnerRace);
        verify(runnerRaceRepository, times(2))
                .updateRunnerRace(runnerRace);
        Assert.assertTrue(actualResult);
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

    @Test
    public void testGetReport() {
        Optional<List<RunnerResult>> runnerResults
                = Optional.of(new ArrayList<>());
        when(runnerRaceRepository.getReport(2019, 1))
                .thenReturn(Optional.of(new ArrayList<>()));
        Optional<List<RunnerResult>> actualRunnerResults
                = runnerRaceRepository.getReport(2019, 1);
        runnerRaceController.getReport(2019, 1);
        verify(runnerRaceRepository, times(2))
                .getReport(2019, 1);
        Assert.assertEquals(actualRunnerResults, runnerResults);
    }
}
