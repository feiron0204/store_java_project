package model;

import java.sql.Timestamp;
import java.util.Calendar;

public class ActorDTO {
    private int actorId;
    private String firstName;
    private String lastName;
    private Calendar lastUpdate;
    
    public int getActorId() {
        return actorId;
    }
    public void setActorId(int actorId) {
        this.actorId = actorId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        if(o instanceof ActorDTO) {
            ActorDTO a = (ActorDTO)o;
            return actorId==a.actorId;
        }
        
        return false;
    }
}
