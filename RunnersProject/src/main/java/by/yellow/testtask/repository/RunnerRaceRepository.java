package by.yellow.testtask.repository;

import by.yellow.testtask.model.RunnerRace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RunnerRaceRepository
        extends JpaRepository<RunnerRace, Integer>, RunnerRaceRepositoryCustom {

    @Query(value = "SELECT * FROM runners_races WHERE runner_id = ?1",
            nativeQuery = true)
    Optional<List<RunnerRace>> getAllRaceByRunnerId(int runnerId);
}
