package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.DBConnector;
import model.StoreDTO;

public class StoreController {
    private Connection conn;

    public StoreController(DBConnector connector) {
        conn = connector.makeConnection();
    }

    public void insert(StoreDTO s) {
        String query = new String(
                "INSERT INTO `store`(`manager_staff_id`,`address_id`,`last_update`)VALUES(?,?,NOW())");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, s.getManagerStaffId());
            pstmt.setInt(2, s.getAddressId());

            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(StoreDTO s) {
        String query = new String(
                "UPDATE `store` SET `manager_staff_id`=?,`address_id`=?,`last_update`=NOW() WHERE `store_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, s.getManagerStaffId());
            pstmt.setInt(2, s.getAddressId());
            pstmt.setInt(3, s.getStoreId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(StoreDTO s) {
        String query = new String("DELETE FROM `store` WHERE `store_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, s.getStoreId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByAddressId(int addressId) {
        String query = new String("DELETE FROM `store` WHERE `address_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, addressId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<StoreDTO> selectAll() {
        ArrayList<StoreDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `store`");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    StoreDTO s = new StoreDTO();
                    s.setStoreId(rs.getInt("store_id"));
                    s.setManagerStaffId(rs.getInt("manager_staff_id"));
                    s.setAddressId(rs.getInt("address_id"));
                    s.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(s);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<StoreDTO> selectAllByManagerStaffId(int managerStaffId) {
        ArrayList<StoreDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `store`WHERE`manager_staff_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, managerStaffId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    StoreDTO s = new StoreDTO();
                    s.setStoreId(rs.getInt("store_id"));
                    s.setManagerStaffId(rs.getInt("manager_staff_id"));
                    s.setAddressId(rs.getInt("address_id"));
                    s.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(s);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ArrayList<StoreDTO> selectAllByAddressId(int addressId) {
        ArrayList<StoreDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `store`WHERE`address_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, addressId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    StoreDTO s = new StoreDTO();
                    s.setStoreId(rs.getInt("store_id"));
                    s.setManagerStaffId(rs.getInt("manager_staff_id"));
                    s.setAddressId(rs.getInt("address_id"));
                    s.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(s);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public StoreDTO selectOne(int storeId) {
        StoreDTO s = null;
        String query = new String("SELECT * FROM `store`WHERE`store_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, storeId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                s = new StoreDTO();
                s.setStoreId(rs.getInt("store_id"));
                s.setManagerStaffId(rs.getInt("manager_staff_id"));
                s.setAddressId(rs.getInt("address_id"));
                s.setLastUpdate(rs.getTimestamp("last_update"));

            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }
}
