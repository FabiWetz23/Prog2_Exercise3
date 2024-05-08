package at.ac.fhcampuswien.fhmdb.models;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;

public class MovieRepository {
    private final Dao<MovieEntity, Long> dao;

    public MovieRepository(ConnectionSource connectionSource) throws SQLException {
        dao = DaoManager.createDao(connectionSource, MovieEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
    }

    public List<MovieEntity> getAllMovies() throws SQLException {
        return dao.queryForAll();
    }

    public int removeAll() throws SQLException {
        return dao.deleteBuilder().delete();
    }

    public MovieEntity getMovie(long id) throws SQLException {
        return dao.queryForId(id);
    }

    public int addAllMovies(List<MovieEntity> movies) throws SQLException {
        return dao.create(movies);
    }
}
