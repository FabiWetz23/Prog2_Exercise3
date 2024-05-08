package at.ac.fhcampuswien.fhmdb.models;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    private final Dao<WatchlistMovieEntity, Long> dao;

    public WatchlistRepository(ConnectionSource connectionSource) throws SQLException {
        dao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
    }

    public List<WatchlistMovieEntity> getWatchlist() throws SQLException {
        return dao.queryForAll();
    }

    public int addToWatchlist(WatchlistMovieEntity movie) throws SQLException {
        return dao.create(movie);
    }

    public int removeFromWatchlist(String apiId) throws SQLException {
        // Create a delete builder
        DeleteBuilder<WatchlistMovieEntity, Long> deleteBuilder = dao.deleteBuilder();

        // Set the where clause to delete entries with the specified API ID
        deleteBuilder.where().eq("apiId", apiId);

        // Execute the delete operation and return the number of rows affected
        return deleteBuilder.delete();
    }


}
