package by.yellow.testtask.repository;

import by.yellow.testtask.model.Runner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RunnerRepository extends JpaRepository<Runner, Integer> {
    @Query(value = "SELECT * FROM runners r where r.login = ?1",
            nativeQuery = true)
    Optional<Runner> findByLoginAndPassword(String login);
}
