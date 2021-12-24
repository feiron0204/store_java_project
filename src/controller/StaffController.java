package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.DBConnector;
import model.StaffDTO;

public class StaffController {
    private Connection conn;

    public StaffController(DBConnector connector) {
        conn = connector.makeConnection();
    }

    public void insert(StaffDTO s) {
        String query = new String(
                "INSERT INTO `staff`(`first_name`,`last_name`,`address_id`,`email`,`store_id`,`username`,`password`,`last_update`)VALUES(?,?,?,?,?,?,?,NOW())");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, s.getFirstName());
            pstmt.setString(2, s.getLastName());
            pstmt.setInt(3, s.getAddressId());
            pstmt.setString(4, s.getEmail());
            pstmt.setInt(5, s.getStoreId());
            pstmt.setString(6, s.getUsername());
            pstmt.setString(7, s.getPassword());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(StaffDTO s) {
        String query = new String(
                "UPDATE `staff` SET `first_name`=?,`last_name`=?,`address_id`=?,`email`=?,`store_id`=?,`active`=?,`password`=?,`last_update`=NOW() WHERE `staff_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, s.getFirstName());
            pstmt.setString(2, s.getLastName());
            pstmt.setInt(3, s.getAddressId());
            pstmt.setString(4, s.getEmail());
            pstmt.setInt(5, s.getStoreId());
            pstmt.setInt(6, s.getActive());
            pstmt.setString(7, s.getPassword());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(StaffDTO s) {
        String query = new String("DELETE FROM `staff` WHERE `staff_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, s.getStaffId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByAddressId(int addressId) {
        String query = new String("DELETE FROM `staff` WHERE `addreess_id`=?");
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
        String query = new String("DELETE FROM `staff` WHERE `store_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, storeId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<StaffDTO> selectAll() {
        ArrayList<StaffDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `staff`");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    StaffDTO s = new StaffDTO();
                    s.setStaffid(rs.getInt("staff_id"));
                    s.setFirstName(rs.getString("first_name"));
                    s.setLastName(rs.getString("last_name"));
                    s.setAddressId(rs.getInt("address_id"));
                    s.setEmail(rs.getString("email"));
                    s.setStoreId(rs.getInt("store_id"));
                    s.setActive(rs.getInt("active"));
                    s.setUsername(rs.getString("username"));
                    s.setPassword(rs.getString("password"));
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

    public ArrayList<StaffDTO> selectAllByAddressId(int addressId) {
        ArrayList<StaffDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `staff`WHERE`address_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, addressId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    StaffDTO s = new StaffDTO();
                    s.setStaffid(rs.getInt("staff_id"));
                    s.setFirstName(rs.getString("first_name"));
                    s.setLastName(rs.getString("last_name"));
                    s.setAddressId(rs.getInt("address_id"));
                    s.setEmail(rs.getString("email"));
                    s.setStoreId(rs.getInt("store_id"));
                    s.setActive(rs.getInt("active"));
                    s.setUsername(rs.getString("username"));
                    s.setPassword(rs.getString("password"));
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

    public ArrayList<StaffDTO> selectAllByStoreId(int storeId) {
        ArrayList<StaffDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `staff`WHERE `store_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, storeId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    StaffDTO s = new StaffDTO();
                    s.setStaffid(rs.getInt("staff_id"));
                    s.setFirstName(rs.getString("first_name"));
                    s.setLastName(rs.getString("last_name"));
                    s.setAddressId(rs.getInt("address_id"));
                    s.setEmail(rs.getString("email"));
                    s.setStoreId(rs.getInt("store_id"));
                    s.setActive(rs.getInt("active"));
                    s.setUsername(rs.getString("username"));
                    s.setPassword(rs.getString("password"));
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

    public ArrayList<StaffDTO> selectAllByActive(int active) {
        ArrayList<StaffDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `staff`WHERE`active`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, active);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    StaffDTO s = new StaffDTO();
                    s.setStaffid(rs.getInt("staff_id"));
                    s.setFirstName(rs.getString("first_name"));
                    s.setLastName(rs.getString("last_name"));
                    s.setAddressId(rs.getInt("address_id"));
                    s.setEmail(rs.getString("email"));
                    s.setStoreId(rs.getInt("store_id"));
                    s.setActive(rs.getInt("active"));
                    s.setUsername(rs.getString("username"));
                    s.setPassword(rs.getString("password"));
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

    public ArrayList<StaffDTO> selectAllSearchByName(String namePart) {
        ArrayList<StaffDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `staff`WHERE`first_name`LIKE?OR`last_name`LIKE?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + namePart + "%");
            pstmt.setString(2, "%" + namePart + "%");
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    StaffDTO s = new StaffDTO();
                    s.setStaffid(rs.getInt("staff_id"));
                    s.setFirstName(rs.getString("first_name"));
                    s.setLastName(rs.getString("last_name"));
                    s.setAddressId(rs.getInt("address_id"));
                    s.setEmail(rs.getString("email"));
                    s.setStoreId(rs.getInt("store_id"));
                    s.setActive(rs.getInt("active"));
                    s.setUsername(rs.getString("username"));
                    s.setPassword(rs.getString("password"));
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

    public StaffDTO selectOne(int staffId) {
        StaffDTO s = null;
        String query = new String("SELECT * FROM `staff`WHERE`staff_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, staffId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                s = new StaffDTO();
                s.setStaffid(rs.getInt("staff_id"));
                s.setFirstName(rs.getString("first_name"));
                s.setLastName(rs.getString("last_name"));
                s.setAddressId(rs.getInt("address_id"));
                s.setEmail(rs.getString("email"));
                s.setStoreId(rs.getInt("store_id"));
                s.setActive(rs.getInt("active"));
                s.setUsername(rs.getString("username"));
                s.setPassword(rs.getString("password"));
                s.setLastUpdate(rs.getTimestamp("last_update"));

            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }
    
    public StaffDTO auth(String username,String password) {
        StaffDTO s = null;
        String query = new String("SELECT * FROM `staff`WHERE`username`=?AND`password`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                System.out.println("입력정보와 일치하는 스테프가 없습니다.");
            } else {
                s = new StaffDTO();
                s.setStaffid(rs.getInt("staff_id"));
                s.setFirstName(rs.getString("first_name"));
                s.setLastName(rs.getString("last_name"));
                s.setAddressId(rs.getInt("address_id"));
                s.setEmail(rs.getString("email"));
                s.setStoreId(rs.getInt("store_id"));
                s.setActive(rs.getInt("active"));
                s.setUsername(rs.getString("username"));
                s.setPassword(rs.getString("password"));
                s.setLastUpdate(rs.getTimestamp("last_update"));

            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }
    public StaffDTO selectOne(String username) {
        StaffDTO s = null;
        String query = new String("SELECT * FROM `staff`WHERE`username`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                s = new StaffDTO();
                s.setStaffid(rs.getInt("staff_id"));
                s.setFirstName(rs.getString("first_name"));
                s.setLastName(rs.getString("last_name"));
                s.setAddressId(rs.getInt("address_id"));
                s.setEmail(rs.getString("email"));
                s.setStoreId(rs.getInt("store_id"));
                s.setActive(rs.getInt("active"));
                s.setUsername(rs.getString("username"));
                s.setPassword(rs.getString("password"));
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
