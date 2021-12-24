package model;

import java.sql.Timestamp;
import java.util.Calendar;

public class FilmActorDTO {
    private int actorId;
    private int filmId;
    private Calendar lastUpdate;
    public int getActorId() {
        return actorId;
    }
    public void setActorId(int actorId) {
        this.actorId = actorId;
    }
    public int getFilmId() {
        return filmId;
    }
    public void setFilmId(int filmId) {
        this.filmId = filmId;
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
        if(o instanceof FilmActorDTO) {
            FilmActorDTO f = (FilmActorDTO)o;
            return actorId==f.actorId&&filmId==f.filmId;
        }
        return false;
    }
}
