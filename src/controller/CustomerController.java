package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.DBConnector;
import model.CustomerDTO;

public class CustomerController {
private Connection conn;
    
    public CustomerController(DBConnector connector) {
        conn = connector.makeConnection();
    }
    public void insert(CustomerDTO c) {
        String query = new String("INSERT INTO `customer`(`store_id`,`first_name`,`last_name`,`email`,`address_id`,`active`,`create_date`,`last_update`)VALUES(?,?,?,?,?,1,NOW(),NOW())");
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,c.getStoreId());
            pstmt.setString(2, c.getFirstName());
            pstmt.setString(3, c.getLastName());
            pstmt.setString(4, c.getEmail());
            pstmt.setInt(5, c.getAddressId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(CustomerDTO c) {
        String query = new String("UPDATE `customer` SET `first_name`=?, `last_name`=?, `email`=?, `address_id`=?,`active`=?,`last_update`=NOW() WHERE `customer_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, c.getFirstName());
            pstmt.setString(2, c.getLastName());
            pstmt.setString(3, c.getEmail());
            pstmt.setInt(4, c.getActive());
            pstmt.setInt(5, c.getAddressId());
            pstmt.setInt(6, c.getCustomerId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(CustomerDTO c) {
        String query = new String("DELETE FROM `customer` WHERE `customer_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, c.getCustomerId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteByAddressId(int addressId) {
        String query = new String("DELETE FROM `customer` WHERE `address_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, addressId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteByStoreId(int storeId) {
        String query = new String("DELETE FROM `customer` WHERE `store_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, storeId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public CustomerDTO auth(String firstName,String lastName) {
        CustomerDTO c = null;
        String query = new String("SELECT * FROM `customer` WHERE `first_name`=? AND `last_name`=?");
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    c = new CustomerDTO();
                    c.setCustomerId(rs.getInt("customer_id"));
                    c.setStoreId(rs.getInt("store_id"));
                    c.setFirstName(rs.getString("first_name"));
                    c.setLastName(rs.getString("last_name"));
                    c.setEmail(rs.getString("email"));
                    c.setAddressId(rs.getInt("address_id"));
                    c.setActive(rs.getInt("active"));
                    c.setCreateDate(rs.getTimestamp("create_date"));
                    c.setLastUpdate(rs.getTimestamp("last_update"));
                }
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
    public CustomerDTO selectOne(int customerId) {
        CustomerDTO c = null;
        String query = new String("SELECT * FROM `customer` WHERE `customer_id`=?");
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    c = new CustomerDTO();
                    c.setCustomerId(rs.getInt("customer_id"));
                    c.setStoreId(rs.getInt("store_id"));
                    c.setFirstName(rs.getString("first_name"));
                    c.setLastName(rs.getString("last_name"));
                    c.setEmail(rs.getString("email"));
                    c.setAddressId(rs.getInt("address_id"));
                    c.setActive(rs.getInt("active"));
                    c.setCreateDate(rs.getTimestamp("create_date"));
                    c.setLastUpdate(rs.getTimestamp("last_update"));
                }
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public ArrayList<CustomerDTO> selectAll() {
        ArrayList<CustomerDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `customer`");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    CustomerDTO c = new CustomerDTO();
                    c.setCustomerId(rs.getInt("customer_id"));
                    c.setStoreId(rs.getInt("store_id"));
                    c.setFirstName(rs.getString("first_name"));
                    c.setLastName(rs.getString("last_name"));
                    c.setEmail(rs.getString("email"));
                    c.setAddressId(rs.getInt("address_id"));
                    c.setActive(rs.getInt("active"));
                    c.setCreateDate(rs.getTimestamp("create_date"));
                    c.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(c);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<CustomerDTO> selectAllSearchName(String namePart) {
        ArrayList<CustomerDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `customer` WHERE `first_name` LIKE ? OR `last_name` LIKE ?");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + namePart + "%");
            pstmt.setString(2, "%"+namePart+"%");
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    CustomerDTO c = new CustomerDTO();
                    c.setCustomerId(rs.getInt("customer_id"));
                    c.setStoreId(rs.getInt("store_id"));
                    c.setFirstName(rs.getString("first_name"));
                    c.setLastName(rs.getString("last_name"));
                    c.setEmail(rs.getString("email"));
                    c.setAddressId(rs.getInt("address_id"));
                    c.setActive(rs.getInt("active"));
                    c.setCreateDate(rs.getTimestamp("create_date"));
                    c.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(c);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<CustomerDTO> selectAllByStoreId(int storeId) {
        ArrayList<CustomerDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `customer` WHERE `store_id` = ?");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, storeId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    CustomerDTO c = new CustomerDTO();
                    c.setCustomerId(rs.getInt("customer_id"));
                    c.setStoreId(rs.getInt("store_id"));
                    c.setFirstName(rs.getString("first_name"));
                    c.setLastName(rs.getString("last_name"));
                    c.setEmail(rs.getString("email"));
                    c.setAddressId(rs.getInt("address_id"));
                    c.setActive(rs.getInt("active"));
                    c.setCreateDate(rs.getTimestamp("create_date"));
                    c.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(c);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
}
