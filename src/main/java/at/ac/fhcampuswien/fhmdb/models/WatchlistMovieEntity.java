package at.ac.fhcampuswien.fhmdb.models;

public class WatchlistMovieEntity extends MovieEntity{

        private long id;
        private String apiId;

        // Konstruktor, Getter und Setter
        public WatchlistMovieEntity(long id, String apiId) {
            this.id = id;
            this.apiId = apiId;
        }

        // Getter und Setter
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getApiId() {
            return apiId;
        }

        public void setApiId(String apiId) {
            this.apiId = apiId;
        }
    }


