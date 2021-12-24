package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.DBConnector;
import model.LanguageDTO;

public class LanguageController {
    private Connection conn;

    public LanguageController(DBConnector connector) {
        conn = connector.makeConnection();
    }

    public void insert(LanguageDTO l) {
        String query = new String("INSERT INTO `language`(`name`,`last_update`)VALUES(?,NOW())");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, l.getName());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public void update() {
//        String query = new String("UPDATE `` SET `` WHERE ``");
//        try {
//            PreparedStatement pstmt = conn.prepareStatement(query);
//            
//            pstmt.executeUpdate();
//            pstmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public void delete(LanguageDTO l) {
        String query = new String("DELETE FROM `language` WHERE `language_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, l.getLanguageId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<LanguageDTO> selectAll() {
        ArrayList<LanguageDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `language`");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);

            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    LanguageDTO l = new LanguageDTO();
                    l.setLanguageId(rs.getInt("language_id"));
                    l.setName(rs.getString("name"));
                    l.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(l);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<LanguageDTO> selectAll(String namePart) {
        ArrayList<LanguageDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `language`WHERE`name`LIKE ?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + namePart + "%");
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    LanguageDTO l = new LanguageDTO();
                    l.setLanguageId(rs.getInt("language_id"));
                    l.setName(rs.getString("name"));
                    l.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(l);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public LanguageDTO selectOne(int languageId) {
        LanguageDTO l = null;
        String query = new String("SELECT * FROM `language`WHERE`language_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, languageId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                l = new LanguageDTO();
                l.setLanguageId(rs.getInt("language_id"));
                l.setName(rs.getString("name"));
                l.setLastUpdate(rs.getTimestamp("last_update"));

            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return l;
    }
}
