package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.DBConnector;
import model.CityDTO;

public class CityController {
private Connection conn;
    
    public CityController(DBConnector connector) {
        conn = connector.makeConnection();
    }
    
    public void insert(CityDTO c) {
        String query = new String("INSERT INTO `city`(`city`,`country_id`,`last_update`)VALUES(?,?,NOW())");
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, c.getCity());
            pstmt.setInt(2, c.getCountryId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(CityDTO c) {
        String query = new String("UPDATE `city` SET `city`=?,`country_id`=?,`last_update`=NOW() WHERE `city_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, c.getCity());
            pstmt.setInt(2, c.getCountryId());
            pstmt.setInt(3, c.getCityId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(CityDTO c) {
        String query = new String("DELETE FROM `city` WHERE `city_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, c.getCityId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteByCountryId(int countryId) {
        String query = new String("DELETE FROM `city` WHERE `country_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, countryId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public CityDTO selectOne(int cityId) {
        CityDTO c = null;
        String query = new String("SELECT * FROM `city` WHERE `city_id`=?");
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, cityId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    c = new CityDTO();
                    c.setCityId(rs.getInt("city_id"));
                    c.setCity(rs.getString("city"));
                    c.setCountryId(rs.getInt("country_id"));
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
    
    public ArrayList<CityDTO> selectAll(){
        ArrayList<CityDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `city`");
        try {
            PreparedStatement pstmt=conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            if(!rs.next()) {
                
            }else {
                rs=pstmt.executeQuery();
                while(rs.next()) {
                    CityDTO c=new CityDTO();
                    c.setCityId(rs.getInt("city_id"));
                    c.setCity(rs.getString("city"));
                    c.setCountryId(rs.getInt("country_id"));
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
    public ArrayList<CityDTO> selectAll(String cityPart,int countryId) {
        ArrayList<CityDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `city` WHERE `city` LIKE ? AND `country_id`=?");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + cityPart + "%");
            pstmt.setInt(2, countryId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    CityDTO c = new CityDTO();
                    c.setCityId(rs.getInt("city_id"));
                    c.setCity(rs.getString("city"));
                    c.setCountryId(rs.getInt("country_id"));
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
    public ArrayList<CityDTO> selectAll(int countryId) {
        ArrayList<CityDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `city` WHERE `country_id`=?");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, countryId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    CityDTO c = new CityDTO();
                    c.setCityId(rs.getInt("city_id"));
                    c.setCity(rs.getString("city"));
                    c.setCountryId(rs.getInt("country_id"));
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
    public ArrayList<CityDTO> selectAll(String cityPart) {
        ArrayList<CityDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `city` WHERE `city` LIKE ? ");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + cityPart + "%");
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    CityDTO c = new CityDTO();
                    c.setCityId(rs.getInt("city_id"));
                    c.setCity(rs.getString("city"));
                    c.setCountryId(rs.getInt("country_id"));
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
//    public String findCityById(int cityId) {
//        String city=null;
//        String query = new String("SELECT * FROM `city` WHERE `city_id`=?");
//        try {
//            PreparedStatement pstmt=conn.prepareStatement(query);
//            pstmt.setInt(1, cityId);
//            ResultSet rs =pstmt.executeQuery();
//            if(rs.next()) {
//                city=new String(rs.getString("city"));
//            }else {
//                city=new String("삭제된 도시");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return city;
//    }
}
