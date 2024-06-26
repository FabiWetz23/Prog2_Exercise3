package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import at.ac.fhcampuswien.fhmdb.ui.WatchlistCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXComboBox releaseYearComboBox;

    @FXML
    public JFXComboBox ratingFromComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies;

    protected ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    protected SortedState sortedState;

    public boolean isWatchlistView = false; // Flag, um anzuzeigen, ob die Watchlist angezeigt werden soll


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeState();
        initializeLayout();
    }

    public void setWatchlistView(boolean isWatchlistView) {
        this.isWatchlistView = isWatchlistView;
        initializeLayout(); // Aktualisierung der Layout-Einstellungen
    }


    public void initializeState() {
        try {
            List<Movie> result = MovieAPI.getAllMovies();
            setMovies(result);
            setMovieList(result);
            sortedState = SortedState.NONE;

            // test stream methods
            System.out.println("getMostPopularActor");
            System.out.println(getMostPopularActor(allMovies));

            System.out.println("getLongestMovieTitle");
            System.out.println(getLongestMovieTitle(allMovies));

            System.out.println("count movies from Zemeckis");
            System.out.println(countMoviesFrom(allMovies, "Robert Zemeckis"));

            System.out.println("count movies from Steven Spielberg");
            System.out.println(countMoviesFrom(allMovies, "Steven Spielberg"));

            System.out.println("getMoviewsBetweenYears");
            List<Movie> between = getMoviesBetweenYears(allMovies, 1994, 2000);
            System.out.println(between.size());
            System.out.println(between.stream().map(Objects::toString).collect(Collectors.joining(", ")));
        } catch (Exception e) {
            System.err.println("Error while initializing state: " + e.getMessage());
            // Handle exceptions
        }
    }

    public void initializeLayout() {
        movieListView.setItems(observableMovies); // Setze die Items der ListView auf die ObservableList
        // Wähle die entsprechende Zellenfabrik basierend auf dem Wert von isWatchlistView
        if (isWatchlistView) {
            movieListView.setCellFactory(movieListView -> new WatchlistCell());
        } else {
            movieListView.setCellFactory(movieListView -> new MovieCell());
        }
        // genre combobox
        Object[] genres = Genre.values();   // get all genres
        genreComboBox.getItems().add("No filter");  // add "no filter" to the combobox
        genreComboBox.getItems().addAll(genres);    // add all genres to the combobox
        genreComboBox.setPromptText("Filter by Genre");

        // year combobox
        releaseYearComboBox.getItems().add("No filter");  // add "no filter" to the combobox
        // fill array with numbers from 1900 to 2023
        Integer[] years = new Integer[124];
        for (int i = 0; i < years.length; i++) {
            years[i] = (Integer) (1900 + i);
        }
        releaseYearComboBox.getItems().addAll(years);    // add all years to the combobox
        releaseYearComboBox.setPromptText("Filter by Release Year");

        // rating combobox
        ratingFromComboBox.getItems().add("No filter");  // add "no filter" to the combobox
        // fill array with numbers from 0 to 10
        Integer[] ratings = new Integer[11];
        for (int i = 0; i < ratings.length; i++) {
            ratings[i] = (Integer) i;
        }
        ratingFromComboBox.getItems().addAll(ratings);    // add all ratings to the combobox
        ratingFromComboBox.setPromptText("Filter by Rating");
    }

    public void setMovies(List<Movie> movies) {
        allMovies = movies;
    }

    public void setMovieList(List<Movie> movies) {
        observableMovies.clear();
        observableMovies.addAll(movies);
    }


    public void sortMovies() {
        if (sortedState == SortedState.NONE || sortedState == SortedState.DESCENDING) {
            sortMovies(SortedState.ASCENDING);
        } else if (sortedState == SortedState.ASCENDING) {
            sortMovies(SortedState.DESCENDING);
        }
    }

    // sort movies based on sortedState
    // by default sorted state is NONE
    // afterwards it switches between ascending and descending
    public void sortMovies(SortedState sortDirection) {
        if (sortDirection == SortedState.ASCENDING) {
            observableMovies.sort(java.util.Comparator.comparing(Movie::getTitle));
            sortedState = SortedState.ASCENDING;
        } else {
            observableMovies.sort(java.util.Comparator.comparing(Movie::getTitle).reversed());
            sortedState = SortedState.DESCENDING;
        }
    }

    public List<Movie> filterByQuery(List<Movie> movies, String query) {
        if (query == null || query.isEmpty()) return movies;

        if (movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream().filter(movie ->
                        movie.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                                movie.getDescription().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    public List<Movie> filterByGenre(List<Movie> movies, Genre genre) {
        if (genre == null) return movies;

        if (movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream().filter(movie -> movie.getGenres().contains(genre)).toList();
    }

    public void applyAllFilters(String searchQuery, Object genre) {
        List<Movie> filteredMovies = allMovies;

        if (!searchQuery.isEmpty()) {
            filteredMovies = filterByQuery(filteredMovies, searchQuery);
        }

        if (genre != null && !genre.toString().equals("No filter")) {
            filteredMovies = filterByGenre(filteredMovies, Genre.valueOf(genre.toString()));
        }

        observableMovies.clear();
        observableMovies.addAll(filteredMovies);
    }

    public void searchBtnClicked(ActionEvent actionEvent) {
        String searchQuery = searchField.getText().trim().toLowerCase();
        String releaseYear = validateComboboxValue(releaseYearComboBox.getSelectionModel().getSelectedItem());
        String ratingFrom = validateComboboxValue(ratingFromComboBox.getSelectionModel().getSelectedItem());
        String genreValue = validateComboboxValue(genreComboBox.getSelectionModel().getSelectedItem());

        Genre genre = null;
        if (genreValue != null) {
            genre = Genre.valueOf(genreValue);
        }

        try {
            if (searchQuery.isEmpty() && genre == null && releaseYear == null && ratingFrom == null) {
                // Wenn keine Filter ausgewählt sind, zeige eine Fehlermeldung an
                showAlert("Error", "No filter criteria selected", "Please select at least one filter criteria.");
                return;
            }

            List<Movie> movies = getMovies(searchQuery, genre, releaseYear, ratingFrom);
            setMovies(movies);
            setMovieList(movies);
            sortMovies(sortedState);
        } catch (MovieApiException e) {
            System.err.println("Error while searching for movies: " + e.getMessage());
            // Handle movie API exception
        } catch (DatabaseException e) {
            System.err.println("Error while searching for movies: " + e.getMessage());
            // Handle database exception
        }
    }

    public String validateComboboxValue(Object value) {
        if (value != null && !value.toString().equals("No filter")) {
            return value.toString();
        }
        return null;
    }

    public List<Movie> getMovies(String searchQuery, Genre genre, String releaseYear, String ratingFrom) throws MovieApiException, DatabaseException {
        return MovieAPI.getAllMovies(searchQuery, genre, releaseYear, ratingFrom);
    }

    public void sortBtnClicked(ActionEvent actionEvent) {
        sortMovies();
    }

    public void fhmdbButtonClicked(ActionEvent actionEvent) {
        isWatchlistView = false;
        loadFXML("home-view.fxml");
    }

    public void watchlistButtonClicked(ActionEvent actionEvent) {
        isWatchlistView = true;
        loadFXML("watchlist-view.fxml");
    }

    private void loadFXML(String fxmlFileName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));

            Scene scene = new Scene(fxmlLoader.load(), 890, 620);

            HomeController controller = fxmlLoader.getController();
            controller.setWatchlistView(isWatchlistView); // Hier wird die Flag an den geladenen Controller übergeben

            Stage stage = (Stage) movieListView.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // count which actor is in the most movies
    public String getMostPopularActor(List<Movie> movies) {
        String actor = movies.stream()
                .flatMap(movie -> movie.getMainCast().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey)
                .orElse("");

        return actor;
    }

    public int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream()
                .mapToInt(movie -> movie.getTitle().length())
                .max()
                .orElse(0);
    }

    public long countMoviesFrom(List<Movie> movies, String director) {
        return movies.stream()
                .filter(movie -> movie.getDirectors().contains(director))
                .count();
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }

    // Methode zum Anzeigen einer Alert-Box für Fehlermeldungen
    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

}
