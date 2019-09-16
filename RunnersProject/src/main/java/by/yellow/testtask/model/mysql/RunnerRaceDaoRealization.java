package by.yellow.testtask.model.mysql;

import by.yellow.testtask.model.dao.RunnerRaceDao;
import by.yellow.testtask.model.entity.RunnerRace;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class RunnerRaceDaoRealization extends BaseDaoRealization
        implements RunnerRaceDao {

    private static final Logger LOGGER
            = LogManager.getLogger(RunnerRaceDaoRealization.class);

    private static final String CLOSE_STATEMENT_EXCEPTION
            = "Impossible to close Statement.";

    private static final String SQL_SCRIPT_UPDATE_DATA_IN_TABLE
            = "UPDATE runners_races r SET r.distance = ?, r.race_time = ?,"
            + " r.race_date = ?, r.race_duration = ?, r.speed = ?"
            + " WHERE r.id = ?";

    @Override
    public void updateRunnerRace(final RunnerRace entity) {
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement(
                    SQL_SCRIPT_UPDATE_DATA_IN_TABLE);

            statement.setDouble(
                    1, entity.getDistance());
            statement.setTime(
                    2, Time.valueOf(entity.getRaceTime()));
            statement.setDate(
                    3, Date.valueOf(entity.getRaceDate()));
            statement.setTime(
                    4, Time.valueOf(entity.getRaceDurationTime()));
            statement.setDouble(
                    5, entity.getSpeed());
            statement.setInt(
                    6, entity.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException | NullPointerException e) {
                LOGGER.error(CLOSE_STATEMENT_EXCEPTION);
            }
        }
    }
}
