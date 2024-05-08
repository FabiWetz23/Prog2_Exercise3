package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
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

    private DatabaseManager() throws DatabaseException {
        try {
            createConnectionSource();
            dao = DaoManager.createDao(connectionSource, MovieEntity.class);
            createTables();
        } catch (SQLException e) {
            throw new DatabaseException("Error initializing database: " + e.getMessage());
        }
    }

    public void createTables() throws DatabaseException {
        try {
            TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
        } catch (SQLException e) {
            throw new DatabaseException("Error creating tables: " + e.getMessage());
        }
    }

    public void createConnectionSource() throws DatabaseException {
        try {
            connectionSource = new JdbcConnectionSource(url, username, password);
        } catch (SQLException e) {
            throw new DatabaseException("Error creating connection source: " + e.getMessage());
        }
    }

    public static DatabaseManager getDatabaseManager() throws DatabaseException {
        if (instance == null) {
            try {
                instance = new DatabaseManager();
            } catch (DatabaseException e) {
                throw new DatabaseException("Error getting database manager: " + e.getMessage());
            }
        }
        return instance;
    }

    public void testDB() throws DatabaseException {
        try {
            MovieEntity movie = new MovieEntity("title", "description", 12, "img", 12, "director", "writer", "Cast", 12, Genre.ACTION);
            dao.create(movie);
        } catch (SQLException e) {
            throw new DatabaseException("Error testing database: " + e.getMessage());
        }
    }

    public Dao<MovieEntity, Long> getDao() {
        return dao;
    }
}
