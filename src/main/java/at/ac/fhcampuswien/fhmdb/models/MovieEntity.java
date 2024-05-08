package at.ac.fhcampuswien.fhmdb.models;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@DatabaseTable (tableName = "Movie")
public class MovieEntity {
    @DatabaseField(generatedId = true)
    public Long id;


    @DatabaseField
    public String title;

    @DatabaseField
    public String description;


    @DatabaseField
    public String genresToString;

    @DatabaseField
    public int releaseYear;

    @DatabaseField
    public String imgUrl;

    @DatabaseField
    public int lengthInMinutes;


    @DatabaseField
    public double rating;

    public MovieEntity(){}

            private static final String CACHE_FILE = "api_cache.json";

            public static JSONObject loadCache() throws IOException, ParseException {
                File file = new File(CACHE_FILE);
                if (file.exists()) {
                    JSONParser parser = new JSONParser();
                    try (FileReader reader = new FileReader(file)) {
                        return (JSONObject) parser.parse(reader);
                    }
                }
                return null;
            }

            public static void saveCache(JSONObject data) throws IOException {
                File file = new File(CACHE_FILE);
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(data.toJSONString());
                }
            }
    public void saveDetailsToCache() throws IOException {
        JSONObject jsonMovie = new JSONObject();
        jsonMovie.put("id", id);
        jsonMovie.put("title", title);
        jsonMovie.put("description", description);
        jsonMovie.put("genresToString", genresToString);
        jsonMovie.put("releaseYear", releaseYear);
        jsonMovie.put("imgUrl", imgUrl);
        jsonMovie.put("lengthInMinutes", lengthInMinutes);
        jsonMovie.put("rating", rating);

        // Speichern des JSON-Objekts in die Cache-Datei
        saveCache(jsonMovie);
    }



    public MovieEntity(String title, String description, int releaseYear, String imgUrl, int lengthInMinutes, String directors, String writers, String mainCast, double rating, Genre genre) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
        this.genresToString = genresToString(genre);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getGenres() {
        return genresToString;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }


    public double getRating() {
        return rating;
    }
    public static String genresToString(Genre genre) {
        return genre.name();
    }
    public static List<MovieEntity> fromMovies(List<Movie> movies) {
        List<MovieEntity> movieEntities = new ArrayList<>();
        for (Movie movie : movies) {
            MovieEntity entity = new MovieEntity();
            entity.title = movie.getTitle();
            entity.description = movie.getDescription();
            entity.releaseYear = movie.getReleaseYear();
            entity.imgUrl = movie.getImgUrl();
            entity.lengthInMinutes = movie.getLengthInMinutes();
            entity.rating = movie.getRating();
            movieEntities.add(entity);
        }

        return movieEntities;
    }






}
