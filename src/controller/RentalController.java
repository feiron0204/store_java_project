package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.DBConnector;
import model.RentalDTO;

public class RentalController {
    private Connection conn;

    public RentalController(DBConnector connector) {
        conn = connector.makeConnection();
    }

    public int insert(RentalDTO r) {
        String query = new String(
                "INSERT INTO `rental`(`rental_date`,`inventory_id`,`customer_id`,`staff_id`,`last_update`)VALUES(NOW(),?,?,?,NOW())");
        int rentalId=0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, r.getInventoryId());
            pstmt.setInt(2, r.getCustormerId());
            pstmt.setInt(3, r.getStaffId());
            pstmt.executeUpdate();
            pstmt.close();
            query = new String ("SELECT LAST_INSERT_ID()");
            pstmt=conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            
            rs.next();
            rentalId= rs.getInt("LAST_INSERT_ID()");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentalId;
    }

    public void update(RentalDTO r) {
        String query = new String("UPDATE `rental` SET `return_date`=NOW(),`last_update`=NOW() WHERE `rental_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, r.getRentalId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(RentalDTO r) {
        String query = new String("DELETE FROM `rental` WHERE `rental_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, r.getRentalId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<RentalDTO> selectAll() {
        ArrayList<RentalDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `rental`");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    RentalDTO r = new RentalDTO();
                    r.setRentalId(rs.getInt("rental_id"));
                    r.setRentalDate(rs.getTimestamp("rental_date"));
                    r.setInventoryId(rs.getInt("inventory_id"));
                    r.setCustormerId(rs.getInt("customer_id"));
                    r.setReturnDate(rs.getTimestamp("return_date"));
                    r.setStaffId(rs.getInt("staff_id"));
                    r.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(r);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countByCustomerId(int customerId) {
        int count=0;
        String query = new String("SELECT COUNT(*) FROM `rental` WHERE `customer_id`=?  GROUP BY `customer_id`");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                
            } else {
                count=rs.getInt(1);
                }
            rs.close();
            pstmt.close();
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    public ArrayList<RentalDTO> selectAllByCustomerId(int customerId,int storeId) {
        ArrayList<RentalDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `rental`INNER JOIN `inventory` ON `rental`.`inventory_id`=`inventory`.`inventory_id` WHERE `customer_id`=? AND `inventory`.`store_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, storeId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    RentalDTO r = new RentalDTO();
                    r.setRentalId(rs.getInt("rental_id"));
                    r.setRentalDate(rs.getTimestamp("rental_date"));
                    r.setInventoryId(rs.getInt("inventory_id"));
                    r.setCustormerId(rs.getInt("customer_id"));
                    if(rs.getTimestamp("return_date")!=null) {
                    r.setReturnDate(rs.getTimestamp("return_date"));
                    }
                    r.setStaffId(rs.getInt("staff_id"));
                    r.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(r);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public RentalDTO selectOne(int rentalId) {
        RentalDTO r = null;
        String query = new String("SELECT * FROM `rental` WHERE `rental_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, rentalId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {

                r = new RentalDTO();
                r.setRentalId(rs.getInt("rental_id"));
                r.setRentalDate(rs.getTimestamp("rental_date"));
                r.setInventoryId(rs.getInt("inventory_id"));
                r.setCustormerId(rs.getInt("customer_id"));
                if(rs.getTimestamp("return_date")!=null) {
                    r.setReturnDate(rs.getTimestamp("return_date"));
                    
                }
                r.setStaffId(rs.getInt("staff_id"));
                r.setLastUpdate(rs.getTimestamp("last_update"));

            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }
    public ArrayList<RentalDTO> selectAllByInventoryId(int inventoryId) {
        ArrayList<RentalDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `rental`WHERE `inventory_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, inventoryId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    RentalDTO r = new RentalDTO();
                    r.setRentalId(rs.getInt("rental_id"));
                    r.setRentalDate(rs.getTimestamp("rental_date"));
                    r.setInventoryId(rs.getInt("inventory_id"));
                    r.setCustormerId(rs.getInt("customer_id"));
                    r.setReturnDate(rs.getTimestamp("return_date"));
                    r.setStaffId(rs.getInt("staff_id"));
                    r.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(r);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ArrayList<RentalDTO> selectAllByStaffId(int staffId) {
        ArrayList<RentalDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `rental`WHERE `staff_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, staffId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    RentalDTO r = new RentalDTO();
                    r.setRentalId(rs.getInt("rental_id"));
                    r.setRentalDate(rs.getTimestamp("rental_date"));
                    r.setInventoryId(rs.getInt("inventory_id"));
                    r.setCustormerId(rs.getInt("customer_id"));
                    r.setReturnDate(rs.getTimestamp("return_date"));
                    r.setStaffId(rs.getInt("staff_id"));
                    r.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(r);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ArrayList<RentalDTO> returnByCustomerId(int customerId,int storeId) {
        ArrayList<RentalDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM sakila.rental INNER JOIN `inventory` ON `inventory`.`inventory_id`=`rental`.`inventory_id` WHERE `return_date` is null AND `customer_id`=? AND `store_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, storeId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    RentalDTO r = new RentalDTO();
                    r.setRentalId(rs.getInt("rental_id"));
                    r.setRentalDate(rs.getTimestamp("rental_date"));
                    r.setInventoryId(rs.getInt("inventory_id"));
                    r.setCustormerId(rs.getInt("customer_id"));
                    if(rs.getTimestamp("return_date")!=null) {
                        r.setReturnDate(rs.getTimestamp("return_date"));
                    }
                    r.setStaffId(rs.getInt("staff_id"));
                    r.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(r);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public int lastUpdateId() {
        String query = new String("SELECT `rental_id` FROM `rental` ORDER BY `rental_id` desc LIMIT 1");
        int rentalId=0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs=pstmt.executeQuery();
            if(!rs.next()) {
                
            }else {
                rentalId=rs.getInt("rental_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentalId;
    }
}
