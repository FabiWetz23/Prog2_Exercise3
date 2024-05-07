import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class MovieEntity {

    private Long id;

    private String title;

    private String genres; // Genres als String, durch "," getrennt

    // Weitere Eigenschaften und Getter/Setters
}
