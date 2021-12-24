package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.DBConnector;
import model.AddressDTO;

public class AddressController {
    private Connection conn;

    public AddressController(DBConnector connector) {
        conn = connector.makeConnection();
    }

    public void insert(AddressDTO a) {
        String query = new String(
                "INSERT INTO `address`(`address`,`address2`,`district`,`city_id`,`postal_code`,`phone`,`last_update`)VALUES(?,?,?,?,?,?,NOW())");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, a.getAddress());
            pstmt.setString(2, a.getAddress2());
            pstmt.setString(3, a.getDistrict());
            pstmt.setInt(4, a.getCityId());
            pstmt.setString(5, a.getPostalCode());
            pstmt.setString(6, a.getPhone());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(AddressDTO a) {
        String query = new String(
                "UPDATE `address` SET `address`=?,`address2`=?,`district`=?,`city_id`=?,`postal_code`=?,`phone`=?,`last_update`=NOW() WHERE `address_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, a.getAddress());
            pstmt.setString(2, a.getAddress2());
            pstmt.setString(3, a.getDistrict());
            pstmt.setInt(4, a.getCityId());
            pstmt.setString(5, a.getPostalCode());
            pstmt.setString(6, a.getPhone());
            pstmt.setInt(7, a.getAddressId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(AddressDTO a) {
        String query = new String("DELETE FROM `address` WHERE `address_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, a.getAddressId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteByCityId(int cityId) {
        String query = new String("DELETE FROM `address` WHERE `city_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, cityId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public AddressDTO selectOne(int addressId) {
        AddressDTO a = null;
        String query = new String("SELECT * FROM `address` WHERE `address_id`=?");
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, addressId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    a = new AddressDTO();
                    a.setAddressId(rs.getInt("address_id"));
                    a.setAddress(rs.getString("address"));
                    a.setAddress2(rs.getString("address2"));
                    a.setDistrict(rs.getString("district"));
                    a.setCityId(rs.getInt("city_id"));
                    a.setPostalCode(rs.getString("postal_code"));
                    a.setPhone(rs.getString("phone"));
                    a.setLastUpdate(rs.getTimestamp("last_update"));
                }
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    public ArrayList<AddressDTO> selectAll() {
        ArrayList<AddressDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `address`");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    AddressDTO a = new AddressDTO();
                    a.setAddressId(rs.getInt("address_id"));
                    a.setAddress(rs.getString("address"));
                    a.setAddress2(rs.getString("address2"));
                    a.setDistrict(rs.getString("district"));
                    a.setCityId(rs.getInt("city_id"));
                    a.setPostalCode(rs.getString("postal_code"));
                    a.setPhone(rs.getString("phone"));
                    a.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(a);
                }
            }
            rs.close();
            pstmt.close();

        } catch (

        SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<AddressDTO> selectAll(String addressPart, int cityId) {
        ArrayList<AddressDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `address` WHERE `address` LIKE ? AND `city_id`=?");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + addressPart + "%");
            pstmt.setInt(2, cityId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    AddressDTO a = new AddressDTO();
                    a.setAddressId(rs.getInt("address_id"));
                    a.setAddress(rs.getString("address"));
                    a.setAddress2(rs.getString("address2"));
                    a.setDistrict(rs.getString("district"));
                    a.setCityId(rs.getInt("city_id"));
                    a.setPostalCode(rs.getString("postal_code"));
                    a.setPhone(rs.getString("phone"));
                    a.setLastUpdate(rs.getTimestamp("last_update"));
                
                    list.add(a);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<AddressDTO> selectAll(int cityId) {
        ArrayList<AddressDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `address` WHERE `city_id`=?");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, cityId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    AddressDTO a = new AddressDTO();
                    a.setAddressId(rs.getInt("address_id"));
                    a.setAddress(rs.getString("address"));
                    a.setAddress2(rs.getString("address2"));
                    a.setDistrict(rs.getString("district"));
                    a.setCityId(rs.getInt("city_id"));
                    a.setPostalCode(rs.getString("postal_code"));
                    a.setPhone(rs.getString("phone"));
                    a.setLastUpdate(rs.getTimestamp("last_update"));
                
                    list.add(a);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<AddressDTO> selectAll(String addressPart) {
        ArrayList<AddressDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `address` WHERE `address` LIKE ? ");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + addressPart + "%");
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    AddressDTO a = new AddressDTO();
                    a.setAddressId(rs.getInt("address_id"));
                    a.setAddress(rs.getString("address"));
                    a.setAddress2(rs.getString("address2"));
                    a.setDistrict(rs.getString("district"));
                    a.setCityId(rs.getInt("city_id"));
                    a.setPostalCode(rs.getString("postal_code"));
                    a.setPhone(rs.getString("phone"));
                    a.setLastUpdate(rs.getTimestamp("last_update"));
                
                    list.add(a);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
//
//    public String findAddressById(int addressId) {
//        String address = null;
//        String query = new String("SELECT * FROM `address` WHERE `address_id`=?");
//        try {
//            PreparedStatement pstmt = conn.prepareStatement(query);
//            pstmt.setInt(1, addressId);
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next()) {
//                address = new String(rs.getString("address"));
//            } else {
//                address = new String("삭제된 주소");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return address;
//    }
//    
//    public String findPhoneById(int addressId) {
//        String phone = null;
//        String query = new String("SELECT * FROM `address` WHERE `address_id`=?");
//        try {
//            PreparedStatement pstmt = conn.prepareStatement(query);
//            pstmt.setInt(1, addressId);
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next()) {
//                phone = new String(rs.getString("phone"));
//            } else {
//                phone = new String("없어진 번호");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return phone;
//    }
//    
    
}
