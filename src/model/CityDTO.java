package model;

import java.sql.Timestamp;
import java.util.Calendar;

public class CityDTO {
    private int cityId;
    private String city;
    private int countryId;
    private Calendar lastUpdate;
    public int getCityId() {
        return cityId;
    }
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public int getCountryId() {
        return countryId;
    }
    public void setCountryId(int countryId) {
        this.countryId = countryId;
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
        if(o instanceof CityDTO) {
            CityDTO c=(CityDTO)o;
            return cityId==c.cityId;
        }
        return false;
    }
}
