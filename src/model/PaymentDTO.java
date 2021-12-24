package model;

import java.sql.Timestamp;
import java.util.Calendar;

public class PaymentDTO {
    private int paymentId;
    private int customerId;
    private int staffId;
    private int rentalId;
    private double amount;
    private Calendar paymentDate;
    private Calendar lastUpdate;
    public int getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public int getStaffId() {
        return staffId;
    }
    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }
    public int getRentalId() {
        return rentalId;
    }
    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public Calendar getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(Calendar paymentDate) {
        this.paymentDate = paymentDate;
    }
    public void setPaymentDate(Timestamp PaymentDate) {
        Calendar c=Calendar.getInstance();
        c.setTime(PaymentDate);
        
        this.paymentDate = c;
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
        if(o instanceof PaymentDTO) {
            PaymentDTO p = (PaymentDTO)o;
            return paymentId==p.paymentId;
        }
        return false;
    }
}
