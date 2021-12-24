package model;

import java.sql.Timestamp;
import java.util.Calendar;

public class CustomerDTO {
    private int customerId;
    private int storeId;
    private String firstName;
    private String lastName;
    private String email;
    private int addressId;
    private int active;
    private Calendar createDate;
    private Calendar lastUpdate;
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public int getStoreId() {
        return storeId;
    }
    public void setStoreId(int storeId) {
        this.storeId = storeId;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getAddressId() {
        return addressId;
    }
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
    public int getActive() {
        return active;
    }
    public void setActive(int active) {
        this.active = active;
    }
    public Calendar getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }
    public void setCreateDate(Timestamp createDate) {
        Calendar c=Calendar.getInstance();
        c.setTime(createDate);
        
        this.createDate = c;
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
        if(o instanceof CustomerDTO) {
            CustomerDTO c = (CustomerDTO)o;
            return customerId==c.customerId;
        }
        return false;
    }
}
