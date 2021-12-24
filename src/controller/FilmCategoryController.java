package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.DBConnector;
import model.FilmCategoryDTO;

public class FilmCategoryController {
private Connection conn;
    
    public FilmCategoryController(DBConnector connector) {
        conn = connector.makeConnection();
    }
    public void insert(FilmCategoryDTO f) {
        String query = new String("INSERT INTO `film_category`(`film_id`,`category_id`,`last_update`)VALUES(?,?,NOW())");
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, f.getFilmId());
            pstmt.setInt(2, f.getCategoryId());
            
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(FilmCategoryDTO f) {
        String query = new String("UPDATE `film_category` SET `category_id`=? WHERE `film_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, f.getCategoryId());
            pstmt.setInt(2, f.getFilmId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    public void delete(FilmCategoryDTO f) {
//        String query = new String("DELETE FROM `film_category` WHERE `film_id`=? AND `category_id`=?");
//        try {
//            PreparedStatement pstmt = conn.prepareStatement(query);
//            pstmt.setInt(1, f.getFilmId());
//            pstmt.setInt(2, f.getCategoryId());
//            pstmt.executeUpdate();
//            pstmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public void deleteByFilmId(int filmId) {
        String query = new String("DELETE FROM `film_category` WHERE `film_id`=? ");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, filmId);
            
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteByCategoryId(int categoryId) {
        String query = new String("DELETE FROM `film_category` WHERE `category_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<FilmCategoryDTO> selectAll() {
        ArrayList<FilmCategoryDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `film_category` ");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    FilmCategoryDTO f = new FilmCategoryDTO();
                    f.setCategoryId(rs.getInt("category_id"));
                    f.setFilmId(rs.getInt("film_id"));
                    f.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(f);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<FilmCategoryDTO> selectAllByCategoryId(int categoryId) {
        ArrayList<FilmCategoryDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `film_category`WHERE `category_id`=? ");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    FilmCategoryDTO f = new FilmCategoryDTO();
                    f.setCategoryId(rs.getInt("category_id"));
                    f.setFilmId(rs.getInt("film_id"));
                    f.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(f);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ArrayList<FilmCategoryDTO> selectAllByFilmId(int filmId) {
        ArrayList<FilmCategoryDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `film_category`WHERE`film_id`=? ");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, filmId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    FilmCategoryDTO f = new FilmCategoryDTO();
                    f.setCategoryId(rs.getInt("category_id"));
                    f.setFilmId(rs.getInt("film_id"));
                    f.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(f);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public FilmCategoryDTO selectOne(int categoryId,int filmId) {
        FilmCategoryDTO f=null;
        String query = new String("SELECT * FROM `film_category`WHERE `category_id`=? AND `film_id`=? ");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            pstmt.setInt(2, filmId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    f = new FilmCategoryDTO();
                    f.setCategoryId(rs.getInt("category_id"));
                    f.setFilmId(rs.getInt("film_id"));
                    f.setLastUpdate(rs.getTimestamp("last_update"));
                    
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return f;
    }
    
}
