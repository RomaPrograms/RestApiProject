package by.yellow.testtask.repository;

import by.yellow.testtask.model.entity.RunnerRace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RunnerRacingRepository
        extends JpaRepository<RunnerRace, Integer> {

    @Query(value = "SELECT * FROM runners_races WHERE runner_id = ?1",
            nativeQuery = true)
    Optional<List<RunnerRace>> getAllRaceByRunnerId(int runnerId);
}
