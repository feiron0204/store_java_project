package model;

import java.sql.Timestamp;
import java.util.Calendar;

public class CountryDTO {
    private int countryId;
    private String country;
    private Calendar lastUpdate;
    public int getCountryId() {
        return countryId;
    }
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
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
        if(o instanceof CountryDTO) {
            CountryDTO c = (CountryDTO)o;
            return countryId==c.countryId;
        }
        return false;
    }
}
