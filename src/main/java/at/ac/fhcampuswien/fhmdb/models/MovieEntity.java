package at.ac.fhcampuswien.fhmdb.models;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable (tableName = "Movie")
public class MovieEntity {
    @DatabaseField(generatedId = true)
    public Long id;


    @DatabaseField
    public String title;

    @DatabaseField
    public String description;

    /*
    @DatabaseField
    private String genres;
*/
    @DatabaseField
    public int releaseYear;

    @DatabaseField
    public String imgUrl;

    @DatabaseField
    public int lengthInMinutes;

    @DatabaseField
    public String directors;

    @DatabaseField
    public String writers;

    @DatabaseField
    public String mainCast;

    @DatabaseField
    public double rating;

    // Weitere Eigenschaften und Getter/Setters

    public MovieEntity(){}
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
