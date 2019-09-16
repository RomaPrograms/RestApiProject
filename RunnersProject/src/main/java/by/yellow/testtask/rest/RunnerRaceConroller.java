package by.yellow.testtask.rest;

import by.yellow.testtask.exception.PersistentException;
import by.yellow.testtask.model.entity.RunnerRace;
import by.yellow.testtask.model.entity.RunnerResult;
import by.yellow.testtask.model.mysql.TransactionFactoryRealization;
import by.yellow.testtask.repository.RunnerRaceRepository;
import by.yellow.testtask.service.RunnerRaceService;
import by.yellow.testtask.service.RunnerResultService;
import by.yellow.testtask.service.ServiceFactoryRealization;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("api/v1/runners")
@RestController
@Api(value = "Runner Racing Resource", description = "Tests runner racing data")
public class RunnerRaceConroller {

    @Autowired
    RunnerRaceRepository racingRepository;

    private static ServiceFactoryRealization serviceRealization;

    static {
        try {
            serviceRealization = new ServiceFactoryRealization(
                    new TransactionFactoryRealization());
        } catch (PersistentException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "Add data about racing for runner")
    @PostMapping("/addRace")
    public ResponseEntity<RunnerRace> addRacing(
            @RequestBody RunnerRace runnerRace) {

        HttpHeaders headers = new HttpHeaders();
        this.racingRepository.save(runnerRace);
        return new ResponseEntity<>(runnerRace, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Gets data about racing of runner")
    @GetMapping("/getRaceById")
    public ResponseEntity<RunnerRace> getRace(@RequestParam("id") int id) {
        HttpHeaders headers = new HttpHeaders();

        Optional<RunnerRace> runnerRace = this.racingRepository.findById(id);

        return runnerRace.isPresent()
                ? new ResponseEntity<>(runnerRace.get(), headers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Gets data about racing of runner")
    @GetMapping("/getAllRaceByRunnerId")
    public ResponseEntity<List<RunnerRace>> getAllRace(
            @RequestParam("runnerId") int runnerId) {

        HttpHeaders headers = new HttpHeaders();

        Optional<List<RunnerRace>> runnerRace
                = this.racingRepository.getAllRaceByRunnerId(runnerId);

        return runnerRace.isPresent()
                ? new ResponseEntity<>(runnerRace.get(), headers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @ApiOperation(value = "Updates data about racing of runner")
    @PutMapping("/updateRaceById")
    public ResponseEntity<RunnerRace> updateRaceById(
            @RequestBody RunnerRace runnerRace) {

        HttpHeaders headers = new HttpHeaders();

        try {
            RunnerRaceService runnerService
                    = serviceRealization.getService(RunnerRaceService.class);
            runnerService.updateRunnerRace(runnerRace);
        } catch (PersistentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(runnerRace, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Get result of all races in weeks during the year")
    @GetMapping("/getReport")
    public ResponseEntity<List<RunnerResult>> getReport(
            @RequestParam("year") int year,
            @RequestParam("id") int id) {
        HttpHeaders headers = new HttpHeaders();

        Optional<List<RunnerResult>> runnerResults;

        try {
            RunnerResultService runnerService
                    = serviceRealization.getService(RunnerResultService.class);
            runnerResults = runnerService.getReport(year, id);
        } catch (PersistentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return runnerResults.isPresent()
                ? new ResponseEntity<>(runnerResults.get(), headers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
