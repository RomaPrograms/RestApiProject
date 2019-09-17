package by.yellow.testtask.RunnersProject;

import by.yellow.testtask.model.Runner;
import by.yellow.testtask.repository.RunnerRepository;
import by.yellow.testtask.rest.RunnerController;
import jbcrypt.BCrypt;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RunnerControllerTests {
    @Mock
    private RunnerRepository runnerRepository;

    @InjectMocks
    private RunnerController runnerController;

    private static Runner runner;

    @BeforeClass
    public static void setUp() {
        runner = new Runner("newLogin", "newPassword");
        String hashedPassword
                = BCrypt.hashpw(runner.getPassword(), BCrypt.gensalt());
        runner.setPassword(hashedPassword);
    }

    @Test
    public void testLogin() {
        when(runnerRepository.findByLoginAndPassword("newLogin"))
                .thenReturn(Optional.of(runner));
        Optional<Runner> actualRunner
                = runnerRepository.findByLoginAndPassword("newLogin");
        runnerController.findByPasswordAndLogin(runner);
        verify(runnerRepository, times(2))
                .findByLoginAndPassword("newLogin");
        Assert.assertEquals(actualRunner,Optional.of(runner));
    }

    @Test
    public void testSignUp() {
        runnerController.createAccount(runner);
        verify(runnerRepository, times(1))
                .save(runner);
    }
}
