package at.ac.fhcampuswien.fhmdb.models;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable (tableName = "Movie")
public class MovieEntity {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField()
    private String title;
    @DatabaseField()
    private String genres; // Genres als String, durch "," getrennt

    // Weitere Eigenschaften und Getter/Setters
}
