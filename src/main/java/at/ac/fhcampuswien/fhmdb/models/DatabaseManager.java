package at.ac.fhcampuswien.fhmdb.models;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseManager {

    private static final String url = "jdbc:h2:file:./db/moviesdb";
    private static final String username = "username";
    private static final String password = "pass";
    private static ConnectionSource connectionSource;
    private Dao<MovieEntity, Long> dao;
    private static DatabaseManager instance;



    private DatabaseManager() throws SQLException {
        createConnectionSource();
        dao = DaoManager.createDao(connectionSource, MovieEntity.class);
        createTables();
    }

    private void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
    }

    private void createConnectionSource() throws SQLException {
        connectionSource = new JdbcConnectionSource(url, username, password);
    }

    public static DatabaseManager getDatabaseManager() throws SQLException {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void testDB() throws SQLException {
        MovieEntity movie = new MovieEntity("title", "description", 12, "img", 12, "director", "writer", "Cast", 12);
        dao.create(movie);
    }

}
