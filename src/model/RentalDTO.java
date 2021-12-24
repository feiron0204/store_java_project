package model;

import java.sql.Timestamp;
import java.util.Calendar;

public class RentalDTO {
    private int rentalId;
    private Calendar rentalDate;
    private int inventoryId;
    private int custormerId;
    private Calendar returnDate;
    private int staffId;
    private Calendar lastUpdate;
    public int getRentalId() {
        return rentalId;
    }
    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }
    public Calendar getRentalDate() {
        return rentalDate;
    }
    public void setRentalDate(Calendar rentalDate) {
        this.rentalDate = rentalDate;
    }
    public void setRentalDate(Timestamp rentalDate) {
        Calendar c=Calendar.getInstance();
        c.setTime(rentalDate);
        
        this.rentalDate = c;
    }
    public int getInventoryId() {
        return inventoryId;
    }
    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }
    public int getCustormerId() {
        return custormerId;
    }
    public void setCustormerId(int custormerId) {
        this.custormerId = custormerId;
    }
    public Calendar getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(Calendar returnDate) {
        this.returnDate = returnDate;
    }
    public void setReturnDate(Timestamp returnDate) {
        Calendar c=Calendar.getInstance();
        c.setTime(returnDate);
        
        this.returnDate = c;
    }
    public int getStaffId() {
        return staffId;
    }
    public void setStaffId(int staffId) {
        this.staffId = staffId;
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
        if(o instanceof RentalDTO) {
            RentalDTO r=(RentalDTO)o;
            return rentalId==r.rentalId;
        }
        return false;
    }
}
