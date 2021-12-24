package model;

import java.sql.Timestamp;
import java.util.Calendar;

public class FilmDTO {
    private int filmId;
    private String title;
    private String description;
    private int releaseYear;
    private int languageId;
    private int originalLanguageId;
    private int rentalDuration;
    private double rentalRate;
    private int length;
    private double replacementCost;
    private String rating;
    private String specialFeatures;
    private Calendar lastUpdate;
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
    public int getReleaseYear() {
        return releaseYear;
    }
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
    public int getLanguageId() {
        return languageId;
    }
    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }
    public int getOriginalLanguageId() {
        return originalLanguageId;
    }
    public void setOriginalLanguageId(int originalLanguageId) {
        this.originalLanguageId = originalLanguageId;
    }
    public int getRentalDuration() {
        return rentalDuration;
    }
    public void setRentalDuration(int rentalDuration) {
        this.rentalDuration = rentalDuration;
    }
    public double getRentalRate() {
        return rentalRate;
    }
    public void setRentalRate(double rentalRate) {
        this.rentalRate = rentalRate;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public double getReplacementCost() {
        return replacementCost;
    }
    public void setReplacementCost(double replacementCost) {
        this.replacementCost = replacementCost;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getSpecialFeatures() {
        return specialFeatures;
    }
    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
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
        if(o instanceof FilmDTO) {
            FilmDTO f=(FilmDTO)o;
            return filmId==f.filmId;
        }
        return false;
    }
}
