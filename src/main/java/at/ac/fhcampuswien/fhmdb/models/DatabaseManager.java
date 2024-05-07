package at.ac.fhcampuswien.fhmdb.models;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String url = "jdbc:h2:file: ./db/moviesdb";;
    private static final String username="username";
    private static final String password="pass";
    private static ConnectionSource connectionSource;
    Dao<MovieEntity, Long> dao;

    private static void createConnectionSource() throws SQLException {
        JdbcConnectionSource source = new JdbcConnectionSource(url, username, password);
    }


}
