package by.yellow.testtask.model.mysql;

import by.yellow.testtask.exception.PersistentException;
import by.yellow.testtask.model.dao.RunnerResultDao;
import by.yellow.testtask.model.entity.RunnerResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RunnerResultDaoRealization extends BaseDaoRealization
        implements RunnerResultDao {

    private static final Logger LOGGER
            = LogManager.getLogger(RunnerResultDaoRealization.class);

    private static final String CLOSE_RESULT_SET_EXCEPTION
            = "Impossible to close ResultSet.";

    private static final String CLOSE_STATEMENT_EXCEPTION
            = "Impossible to close Statement.";

    private static final String SQL_EXCEPTION
            = "Some exception connected with SQL.";

    private static final String SQL_SCRIPT_GET_REPORT
            = "select week(race_date, 1) as nedelya,"
            + " date_sub(race_date, interval (weekday(race_date)) day),"
            + " date_add(race_date, interval (6 - weekday(race_date)) day),"
            + " sum(distance), avg(speed), avg(race_duration) from runners_races"
            + " where (extract(year from race_date)) = (?) and runner_id = (?)"
            + " group by week(race_date, 1) order by nedelya asc";

    @Override
    public Optional<List<RunnerResult>> getReport(int year, int id)
            throws PersistentException {

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<RunnerResult> runnerResults = new ArrayList<>();

        try {
            statement = getConnection().prepareStatement(
                    SQL_SCRIPT_GET_REPORT);
            statement.setInt(1, year);
            statement.setInt(2, id);
            resultSet = statement.executeQuery();
            RunnerResult runnerResult;

            while (resultSet.next()) {
                runnerResult = new RunnerResult();
                runnerResult.setWeek(resultSet.getInt(1));
                runnerResult.setStartWeek(
                        resultSet.getDate(2).toLocalDate());
                runnerResult.setEndWeek(
                        resultSet.getDate(3).toLocalDate());
                runnerResult.setDistance(resultSet.getDouble(4));
                runnerResult.setSpeed(resultSet.getDouble(5));
                runnerResult.setRaceDuration(resultSet.getLong(6));
                runnerResults.add(runnerResult);
            }

            return Optional.of(runnerResults);
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION);
            throw new PersistentException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                LOGGER.error(CLOSE_STATEMENT_EXCEPTION);
            }
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                LOGGER.error(CLOSE_RESULT_SET_EXCEPTION);
            }
        }
    }
}
