package at.ac.fhcampuswien.fhmdb.models;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String url = "jdbc:h2:file: ./db/moviesdb";;
    private static final String username="username";
    private static final String password="pass";
    private static ConnectionSource connectionSource;
    Dao<MovieEntity, Long> dao;
    private static DatabaseManager instance;
    public void TestDB() throws SQLException {
        MovieEntity movie = new MovieEntity("title","description",12,"img",12,"director","writer","Cast",12);
        dao.create(movie);
    }

    private DatabaseManager(){
        try {
            createConnectionSource();
            dao= DaoManager.createDao(connectionSource, MovieEntity.class);
            createTables();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        }
        private static void createTables() throws SQLException {
            TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
        }
        public static DatabaseManager getDatabaseManager(){
            if(instance==null){
                instance = new DatabaseManager();
        }
            return instance;
    }
    private static void createConnectionSource() throws SQLException {
        JdbcConnectionSource source = new JdbcConnectionSource(url, username, password);
    }


}
