package by.yellow.testtask.rest;

import by.yellow.testtask.model.RunnerRace;
import by.yellow.testtask.model.RunnerResult;
import by.yellow.testtask.repository.RunnerRacingRepository;
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
public class RunnerRacingConroller {

    @Autowired
    RunnerRacingRepository racingRepository;

    @ApiOperation(value = "Add data about racing for runner")
    @PostMapping("/addRacing")
    public ResponseEntity<RunnerRace> addRacing(
            @RequestBody RunnerRace runnerRace) {

        HttpHeaders headers = new HttpHeaders();
        double speed = runnerRace.getDistance()/runnerRace
                .getRaceDurationTime().toSecondOfDay();
        runnerRace.setSpeed(speed);
        this.racingRepository.save(
                runnerRace.getDistance(), runnerRace.getRaceTime(),
                runnerRace.getRaceDate(), runnerRace.getSpeed(),
                runnerRace.getRaceDurationTime(), runnerRace.getRunnerId());
        return new ResponseEntity<>(runnerRace, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Gets data about racing of runner")
    @GetMapping("/getRaceById")
    public ResponseEntity<RunnerRace> getRace(@RequestParam("id") int id){
        HttpHeaders headers = new HttpHeaders();

        Optional<RunnerRace> runnerRace = this.racingRepository.findById(id);

        return runnerRace.isPresent()
                ? new ResponseEntity<>(runnerRace.get(), headers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @ApiOperation(value = "Gets data about racing of runner")
    @GetMapping("/getAllRaceByRunnerId")
    public ResponseEntity<List<RunnerRace>> getAllRace(
            @RequestParam("runnerId") int runnerId){

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

        double speed = runnerRace.getDistance()/runnerRace
                .getRaceDurationTime().toSecondOfDay();
        runnerRace.setSpeed(speed);
//        this.racingRepository.updateRunnerRaceById(
//                runnerRace.getDistance(),
//                runnerRace.getRaceTime(),
//                runnerRace.getRaceDate(),
//                runnerRace.getRaceDurationTime(),
//                runnerRace.getSpeed(),
//                runnerRace.getRunnerId());
        return new ResponseEntity<>(runnerRace, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Get result of all races in weeks during the year")
    @GetMapping("/getReport")
    public ResponseEntity<List<RunnerResult>> getReport(@RequestParam("year") int year,
                                        @RequestParam("id") int id) {
        HttpHeaders headers = new HttpHeaders();

        Optional<List<RunnerResult>> runnerResults
                = this.racingRepository.getReport(year, id);
        return runnerResults.isPresent()
                ? new ResponseEntity<>(runnerResults.get(), headers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
