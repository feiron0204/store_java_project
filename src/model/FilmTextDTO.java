package model;

public class FilmTextDTO {
    private int filmId;
    private String title;
    private String description;
    public int getFilmId() {
        return filmId;
    }
    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean equals(Object o) {
        if(o instanceof FilmTextDTO) {
            FilmTextDTO f = (FilmTextDTO)o;
            return filmId==f.filmId;
        }
        return false;
    }
}
