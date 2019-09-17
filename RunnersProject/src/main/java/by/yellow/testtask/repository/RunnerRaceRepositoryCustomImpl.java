package by.yellow.testtask.repository;

import by.yellow.testtask.model.RunnerRace;
import by.yellow.testtask.model.RunnerResult;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RunnerRaceRepositoryCustomImpl
        implements RunnerRaceRepositoryCustom {

    private static final String SQL_SCRIPT_GET_REPORT
            = "select week(race_date_and_time, 1) as week,"
            + " date_sub(race_date_and_time, interval (weekday(race_date_and_time)) day),"
            + " date_add(race_date_and_time, interval (6 - weekday(race_date_and_time)) day),"
            + " sum(distance), avg(speed), avg(race_duration) from runners_races"
            + " where (extract(year from race_date_and_time)) = (?) and runner_id = (?)"
            + " group by week(race_date_and_time, 1) order by week asc";

    private static final String SQL_SCRIPT_UPDATE_DATA_IN_TABLE
            = "UPDATE runners_races r SET r.distance = ?,"
            + " r.race_date_and_time = ?, r.race_duration = ?, r.speed = ?"
            + " WHERE r.id = ?";

    private static EntityManager entityManager;

    public RunnerRaceRepositoryCustomImpl() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(
                "Persist" );
        entityManager = emf.createEntityManager();
    }

    @Override
    public Optional<List<RunnerResult>> getReport(int year, int runnerId) {
        List<RunnerResult> runnerResults = new ArrayList<>();
        List<Object[]> results;

        Query query = entityManager.createNativeQuery(SQL_SCRIPT_GET_REPORT);
        query.setParameter(1, year);
        query.setParameter(2, runnerId);
        results = query.getResultList();
        RunnerResult runnerResult;
        for(Object[] object : results) {
            runnerResult = new RunnerResult();
            runnerResult.setWeek((int) object[0]);
            runnerResult.setStartWeek(((Timestamp) object[1])
                    .toLocalDateTime().toLocalDate());
            runnerResult.setEndWeek(((Timestamp) object[2])
                    .toLocalDateTime().toLocalDate());
            runnerResult.setDistance((double) object[3]);
            runnerResult.setSpeed((double) object[4]);
            runnerResult.setRaceDuration(((BigDecimal) object[5]).longValue());
            runnerResults.add(runnerResult);
        }

        return Optional.of(runnerResults);
    }

    @Override
    public boolean updateRunnerRace(RunnerRace runnerRace) {
        EntityTransaction transaction = null;
        Query query = entityManager.createNativeQuery(
                SQL_SCRIPT_UPDATE_DATA_IN_TABLE);

        try {
            query.setParameter(
                    1, runnerRace.getDistance());
            query.setParameter(
                    2, runnerRace.getRaceDate());
            query.setParameter(
                    3, runnerRace.getRaceDurationTimeInNano());
            query.setParameter(
                    4, runnerRace.getSpeed());
            query.setParameter(
                    5, runnerRace.getId());

            transaction = entityManager.getTransaction();
            transaction.begin();
            int res = query.executeUpdate();
            transaction.commit();
            return res == 1;
        } catch(Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
        }
        return true;
    }
}
