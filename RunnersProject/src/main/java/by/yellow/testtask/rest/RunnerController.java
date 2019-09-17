package by.yellow.testtask.rest;

import by.yellow.testtask.model.Runner;
import by.yellow.testtask.repository.RunnerRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("api/v1/runners")
@RestController
@Api(value = "Runner Resource",
        description = "Tests runner login and sign up.")
public class RunnerController {

    @Autowired
    private RunnerRepository runnerRepository;

    @ApiOperation(value = "Lets runner to login on the site.")
    @PostMapping("/login")
    public ResponseEntity<Runner> findByPasswordAndLogin(
            @RequestBody Runner runner) {
        HttpHeaders headers = new HttpHeaders();

        Optional<Runner> optionalRunner
                = this.runnerRepository.findByLoginAndPassword(
                runner.getLogin());

        if (optionalRunner.isPresent() && BCrypt.checkpw(runner.getPassword(),
                optionalRunner.get().getPassword())) {
            return new ResponseEntity<>(optionalRunner.get(),
                    headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Lets runner to sign up on the site.")
    @PostMapping("/sign_up")
    public ResponseEntity<Runner> createAccount(
            @RequestBody Runner runner) {
        HttpHeaders headers = new HttpHeaders();

        if (runner == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String hashedPassword
                = BCrypt.hashpw(runner.getPassword(), BCrypt.gensalt());

        runner.setPassword(hashedPassword);

        this.runnerRepository.save(runner);
        return new ResponseEntity<>(runner, headers, HttpStatus.OK);
    }
}
