package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.DatabaseManager;
import at.ac.fhcampuswien.fhmdb.models.MovieEntity;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class FhmdbApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("FHMDb!");
        stage.setScene(scene);
        stage.show();
        try {
            DatabaseManager.getDatabaseManager().testDB();
            // Hier DatabaseManager.getDatabaseManager() aufrufen, um sicherzustellen, dass die Instanz initialisiert ist
            List<MovieEntity> movies = DatabaseManager.getDatabaseManager().getDao().queryForAll();
            for (MovieEntity movie : movies) {
                System.out.println("Titel: " + movie.getTitle());
                System.out.println("Beschreibung: " + movie.getDescription());
                System.out.println("Genres: " + movie.getGenres());
                System.out.println("Veröffentlichungsjahr: " + movie.getReleaseYear());
                System.out.println("Bild-URL: " + movie.getImgUrl());
                System.out.println("Länge (in Minuten): " + movie.getLengthInMinutes());
                System.out.println("Bewertung: " + movie.getRating());
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
