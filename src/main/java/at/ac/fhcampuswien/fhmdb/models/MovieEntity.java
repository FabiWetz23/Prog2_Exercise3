package at.ac.fhcampuswien.fhmdb.models;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable (tableName = "Movie")
public class MovieEntity {
    @DatabaseField(id = true,generatedId = true)
    private Long id;


    @DatabaseField
    private String title;

    @DatabaseField
    private String description;

    /*
    @DatabaseField
    private String genres;
*/
    @DatabaseField
    private int releaseYear;

    @DatabaseField
    private String imgUrl;

    @DatabaseField
    private int lengthInMinutes;

    @DatabaseField
    private String directors;

    @DatabaseField
    private String writers;

    @DatabaseField
    private String mainCast;

    @DatabaseField
    private double rating;

    // Weitere Eigenschaften und Getter/Setters


    public MovieEntity(String title, String description, int releaseYear, String imgUrl, int lengthInMinutes, String directors, String writers, String mainCast, double rating) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.directors = directors;
        this.writers = writers;
        this.mainCast = mainCast;
        this.rating = rating;
    }
}
