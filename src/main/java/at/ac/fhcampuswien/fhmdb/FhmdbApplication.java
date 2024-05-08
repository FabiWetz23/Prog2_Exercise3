package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.DatabaseManager;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieEntity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static at.ac.fhcampuswien.fhmdb.models.MovieEntity.loadCache;
import static at.ac.fhcampuswien.fhmdb.models.MovieEntity.saveCache;

public class FhmdbApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, ParseException {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("FHMDb!");
        stage.setScene(scene);
        stage.show();
        try {
            DatabaseManager databaseManager = DatabaseManager.getDatabaseManager();
            databaseManager.testDB();


            List<MovieEntity> moviesFromDB = databaseManager.getDao().queryForAll();
            for (MovieEntity movie : moviesFromDB) {
                System.out.println("Title: " + movie.getTitle());
                System.out.println("Description: " + movie.getDescription());
                System.out.println("Genres: " + movie.getGenres());
                System.out.println("Release Year: " + movie.getReleaseYear());
                System.out.println("Image URL: " + movie.getImgUrl());
                System.out.println("Length (minutes): " + movie.getLengthInMinutes());
                System.out.println("Rating: " + movie.getRating());
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        try {
            JSONObject cachedData = loadCache();
            if (cachedData != null) {
                System.out.println("Loaded cached data: " + cachedData);
                // Check timestamp and decide whether to refresh data from API
                // ...
            } else {
                System.out.println("No cached data found.");
                // Fetch data from API
                // JSONObject apiData = fetchDataFromAPI();
                // saveCache(apiData);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }}

        public static void main (String[]args){
            launch();
        }
    }
