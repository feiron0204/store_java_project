package model;

import java.sql.Timestamp;
import java.util.Calendar;

public class StoreDTO {
    private int storeId;
    private int managerStaffId;
    private int addressId;
    private Calendar lastUpdate;
    public int getStoreId() {
        return storeId;
    }
    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
    public int getManagerStaffId() {
        return managerStaffId;
    }
    public void setManagerStaffId(int managerStaffId) {
        this.managerStaffId = managerStaffId;
    }
    public int getAddressId() {
        return addressId;
    }
    public void setAddressId(int addressId) {
        this.addressId = addressId;
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
        if(o instanceof StoreDTO) {
            StoreDTO s=(StoreDTO)o;
            return storeId==s.storeId;
        }
        return false;
    }
}
