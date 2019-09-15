package by.yellow.testtask.repository;

import by.yellow.testtask.model.Runner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RunnerRepository extends JpaRepository<Runner, Integer> {
    Optional<Runner> findByLoginAndPassword(String login, String password);
}
