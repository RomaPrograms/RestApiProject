package by.yellow.testtask.repository;

import by.yellow.testtask.model.RunnerRace;

import java.math.BigDecimal;
import java.sql.*;

import by.yellow.testtask.model.RunnerResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface RunnerRacingRepository extends JpaRepository<RunnerRace, Integer> {

    String URL
            = "jdbc:mysql://localhost:3306/runner_db?useUnicode=true"
            + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false"
            + "&serverTimezone=UTC";
    String USER = "root";
    String PASSWORD = "9512684Roma";
    String SQL_SCRIPT_FOR_YEAR_REPORT
            = "select week(race_date, 1),"
            + " date_sub(race_date, interval (weekday(race_date)) day),"
            + " date_add(race_date, interval (6 - weekday(race_date)) day),"
            + " sum(distance), avg(speed), avg(race_duration) from runners_races"
            + " where (select year(race_date)) = (?) and runner_id = (?)";

    @Query(value = "SELECT * FROM runners_races WHERE runner_id = ?1",
            nativeQuery =  true)
    Optional<List<RunnerRace>> getAllRaceByRunnerId(int runnerId);

    @Query(value = "INSERT INTO runners_races (distance, race_time, race_date,"
            + " speed, race_duration, runner_id)"
            + " values (?1, ?2, ?3, ?4, ?5, ?6)",
            nativeQuery = true)
    void save(double distance, LocalTime raceTime, LocalDate raceDate,
              double speed, LocalTime raceDuration, int runnerId);
//    @Modifying
//    @Query("UPDATE runners_races SET distance = ?1, race_time = ?2,"
//            + " race_date = ?3, race_duration = ?4, speed = ?5"
//            + " where runner_id = ?6")
//    void updateRunnerRaceById(double distance, LocalTime raceTime,
//                              LocalDate raceDate, LocalTime raceDuration,
//                              double speed, int runnerId);

    default Optional<List<RunnerResult>> getReport(int year, int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<RunnerResult> runnerResults = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(
                    URL, USER, PASSWORD);
            statement = connection.prepareStatement(SQL_SCRIPT_FOR_YEAR_REPORT);
            statement.setInt(1, year);
            statement.setInt(2, id);
            resultSet = statement.executeQuery();
            RunnerResult runnerResult = null;

            if(resultSet.next()) {
                runnerResult = new RunnerResult();
                runnerResult.setWeek(resultSet.getInt(1));
                runnerResult.setStartWeek(resultSet.getDate(2).toLocalDate());
                runnerResult.setEndWeek(resultSet.getDate(3).toLocalDate());
                runnerResult.setDistance(resultSet.getDouble(4));
                runnerResult.setSpeed(resultSet.getDouble(5));
                String string = resultSet.getBigDecimal(6).toString();
                System.out.println(string);
                System.out.println();
                System.out.println(Integer.parseInt(string.substring(0, 2)));
                System.out.println(Integer.parseInt(string.substring(2, 4)));
                System.out.println(string.substring(7));
                System.out.println(LocalTime.of(
                        Integer.parseInt(string.substring(0, 2)),
                        Integer.parseInt(string.substring(2, 4)),
                        Integer.parseInt(string.substring(4, 6)),
                        Integer.parseInt(string.substring(7))));
                //runnerResult.setRaceDuration(resultSet.getTime(6).toLocalTime());
                runnerResults.add(runnerResult);
            }

            return Optional.of(runnerResults);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return Optional.of(runnerResults);
    }
}
