package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.DBConnector;
import model.PaymentDTO;

public class PaymentController {
private Connection conn;
    
    public PaymentController(DBConnector connector) {
        conn = connector.makeConnection();
    }
    public void insert(PaymentDTO p) {
        String query = new String("INSERT INTO `payment`(`customer_id`,`staff_id`,`rental_id`,`amount`,`payment_date`,`last_update`)VALUES(?,?,?,?,NOW(),NOW())");
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, p.getCustomerId());
            pstmt.setInt(2, p.getStaffId());
            pstmt.setInt(3, p.getRentalId());
            pstmt.setDouble(4, p.getAmount());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(PaymentDTO p) {
        String query = new String("UPDATE `payment` SET `amount`=?,`last_update`=NOW() WHERE `payment_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setDouble(1, p.getAmount());
            pstmt.setInt(2, p.getPaymentId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(PaymentDTO p) {
        String query = new String("DELETE FROM `payment` WHERE `payment_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, p.getPaymentId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<PaymentDTO> selectAll() {
        ArrayList<PaymentDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `payment`");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    PaymentDTO p = new PaymentDTO();
                    p.setPaymentId(rs.getInt("payment_id"));
                    p.setCustomerId(rs.getInt("customer_id"));
                    p.setStaffId(rs.getInt("staff_id"));
                    p.setRentalId(rs.getInt("rental_id"));
                    p.setAmount(rs.getDouble("amount"));
                    p.setPaymentDate(rs.getTimestamp("payment_date"));
                    p.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(p);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public PaymentDTO selectOne(int paymentId) {
        PaymentDTO p =null;
        String query = new String("SELECT * FROM `payment`WHERE`payment_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, paymentId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                     p = new PaymentDTO();
                    p.setPaymentId(rs.getInt("payment_id"));
                    p.setCustomerId(rs.getInt("customer_id"));
                    p.setStaffId(rs.getInt("staff_id"));
                    p.setRentalId(rs.getInt("rental_id"));
                    p.setAmount(rs.getDouble("amount"));
                    p.setPaymentDate(rs.getTimestamp("payment_date"));
                    p.setLastUpdate(rs.getTimestamp("last_update"));
                   
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }
    public ArrayList<PaymentDTO> selectAllByCustomerId(int customerId) {
        ArrayList<PaymentDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `payment`WHERE`payment`.`customer_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    PaymentDTO p = new PaymentDTO();
                    p.setPaymentId(rs.getInt("payment_id"));
                    p.setCustomerId(rs.getInt("customer_id"));
                    p.setStaffId(rs.getInt("staff_id"));
                    p.setRentalId(rs.getInt("rental_id"));
                    p.setAmount(rs.getDouble("amount"));
                    p.setPaymentDate(rs.getTimestamp("payment_date"));
                    p.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(p);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public double sumByCustomerId(int customerId) {
        double sum=0;
        String query = new String("SELECT SUM(`amount`) FROM `payment`WHERE`customer_id`=? GROUP BY `customer_id`");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                
            } else {
                sum=rs.getDouble(1);
                }
            
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }
    public double sumByStoreId(int storeId) {
        double sum=0;
        String query = new String("SELECT sum(`amount`) FROM sakila.payment INNER JOIN `rental` ON `rental`.`rental_id` = `payment`.`rental_id`INNER JOIN `inventory` ON `inventory`.`inventory_id`=`rental`.`inventory_id`WHERE`inventory`.`store_id`=?;");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, storeId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                
            } else {
                sum=rs.getDouble(1);
                }
            
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }
    public ArrayList<PaymentDTO> selectAllByStoreId(int storeId) {
        ArrayList<PaymentDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM sakila.payment INNER JOIN `rental` ON `rental`.`rental_id` = `payment`.`rental_id`INNER JOIN `inventory` ON `inventory`.`inventory_id`=`rental`.`inventory_id`WHERE`inventory`.`store_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, storeId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    PaymentDTO p = new PaymentDTO();
                    p.setPaymentId(rs.getInt("payment_id"));
                    p.setCustomerId(rs.getInt("customer_id"));
                    p.setStaffId(rs.getInt("staff_id"));
                    p.setRentalId(rs.getInt("rental_id"));
                    p.setAmount(rs.getDouble("amount"));
                    p.setPaymentDate(rs.getTimestamp("payment_date"));
                    p.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(p);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public PaymentDTO selectAllByRentalId(int rentalId) {
        PaymentDTO p =null;
        String query = new String("SELECT * FROM `payment`WHERE`rental_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, rentalId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                     p = new PaymentDTO();
                    p.setPaymentId(rs.getInt("payment_id"));
                    p.setCustomerId(rs.getInt("customer_id"));
                    p.setStaffId(rs.getInt("staff_id"));
                    p.setRentalId(rs.getInt("rental_id"));
                    p.setAmount(rs.getDouble("amount"));
                    p.setPaymentDate(rs.getTimestamp("payment_date"));
                    p.setLastUpdate(rs.getTimestamp("last_update"));
                    
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }
}
