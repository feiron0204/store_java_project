package model;

import java.sql.Timestamp;
import java.util.Calendar;

public class LanguageDTO {
    private int languageId;
    private String name;
    private Calendar lastUpdate;
    public int getLanguageId() {
        return languageId;
    }
    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
        if(o instanceof LanguageDTO) {
            LanguageDTO l = (LanguageDTO)o;
            return languageId==l.languageId;
        }
        return false;
    }
}
