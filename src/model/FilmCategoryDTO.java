package model;

import java.sql.Timestamp;
import java.util.Calendar;

public class FilmCategoryDTO {
    private int filmId;
    private int categoryId;
    private Calendar lastUpdate;
    public int getFilmId() {
        return filmId;
    }
    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public Calendar getLastUpdate() {
        return lastUpdate;
    }
    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    public void setLastUpdate(Timestamp lastUpdate) {
        Calendar c=Calendar.getInstance();
        c.setTime(lastUpdate);
        
        this.lastUpdate = c;
    }
    
    public boolean equals(Object o) {
        if(o instanceof FilmCategoryDTO) {
            FilmCategoryDTO f=(FilmCategoryDTO)o;
            return filmId==f.filmId;
        }
        return false;
    }
}
