package at.ac.fhcampuswien.fhmdb.models;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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

    // Weitere Eigenschaften und Getter/Setters

    public MovieEntity(){}
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

}
