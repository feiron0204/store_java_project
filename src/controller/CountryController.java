package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.DBConnector;
import model.CountryDTO;

public class CountryController {
    private Connection conn;

    public CountryController(DBConnector connector) {
        conn = connector.makeConnection();
    }

    public void insert(CountryDTO c) {
        String query = new String("INSERT INTO `country`(`country`,`last_update`)VALUES(?,NOW())");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, c.getCountry());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(CountryDTO c) {
        String query = new String("UPDATE `country` SET `country`=?,`last_update`=NOW() WHERE `country_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, c.getCountry());
            pstmt.setInt(2, c.getCountryId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(CountryDTO c) {
        String query = new String("DELETE FROM `country` WHERE `country_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, c.getCountryId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CountryDTO selectOne(int countryId) {
        CountryDTO c = null;
        String query = new String("SELECT * FROM `country` WHERE `country_id`=?");
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, countryId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    c = new CountryDTO();
                    c.setCountryId(rs.getInt("country_id"));
                    c.setCountry(rs.getString("country"));
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

    public ArrayList<CountryDTO> selectAll() {
        ArrayList<CountryDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `country`");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    CountryDTO c = new CountryDTO();
                    c.setCountryId(rs.getInt("country_id"));
                    c.setCountry(rs.getString("country"));
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

    public ArrayList<CountryDTO> selectAll(String countryPart) {
        ArrayList<CountryDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `country` WHERE `country` LIKE ?");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + countryPart + "%");
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    CountryDTO c = new CountryDTO();
                    c.setCountryId(rs.getInt("country_id"));
                    c.setCountry(rs.getString("country"));
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
//    
//    public String findCountryById(int countryId) {
//        String country=null;
//        String query = new String("SELECT * FROM `country` WHERE `country_id`=?");
//        try {
//            PreparedStatement pstmt=conn.prepareStatement(query);
//            pstmt.setInt(1, countryId);
//            ResultSet rs =pstmt.executeQuery();
//            if(rs.next()) {
//                country=new String(rs.getString("country"));
//            }else {
//                country=new String("삭제된 지역");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return country;
//    }
}
