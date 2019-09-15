package by.yellow.testtask.RunnersProject;

import by.yellow.testtask.repository.RunnerRepository;
import by.yellow.testtask.rest.RunnerController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class RunnerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RunnerRepository runnerRepository;

    @InjectMocks
    private RunnerController runnerController;

    @Before
    public void setUp() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(runnerController).build();
    }

    @Test
    public void testFindByLoginAndPassword() throws Exception{

        //Mockito.when(runnerRepository.findByLoginAndPassword("", "")).thenReturn("hello");

        mockMvc.perform(
                MockMvcRequestBuilders.post("api/v1/runner/login")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello world"));

        //verify(runnerRepository).findById();
    }
}
