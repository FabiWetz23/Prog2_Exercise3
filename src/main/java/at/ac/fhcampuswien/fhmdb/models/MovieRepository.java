package at.ac.fhcampuswien.fhmdb.models;

import java.sql.SQLException;
import java.util.List;

public class MovieRepository {
    private static DatabaseManager databaseManager;

    static {
        try {
            databaseManager = DatabaseManager.getDatabaseManager();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<MovieEntity> getAllMovies() {
        try {
            return databaseManager.getDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
