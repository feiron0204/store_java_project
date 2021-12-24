package model;

import java.sql.Timestamp;
import java.util.Calendar;

public class CategoryDTO {
    private int categoryId;
    private String name;
    private Calendar lastUpdate;
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
        if(o instanceof CategoryDTO) {
            CategoryDTO c=(CategoryDTO)o;
            return categoryId==c.categoryId;
        }
        return false;
    }
}
