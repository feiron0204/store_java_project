package model;

import java.sql.Timestamp;
import java.util.Calendar;

public class InventoryDTO {
    private int inventoryId;
    private int filmId;
    private int storeId;
    private Calendar lastUpdate;
    public int getInventoryId() {
        return inventoryId;
    }
    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }
    public int getFilmId() {
        return filmId;
    }
    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }
    public int getStoreId() {
        return storeId;
    }
    public void setStoreId(int storeId) {
        this.storeId = storeId;
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
        if(o instanceof InventoryDTO) {
            InventoryDTO i = (InventoryDTO)o;
            return inventoryId==i.inventoryId;
        }
        return false;
    }
}
