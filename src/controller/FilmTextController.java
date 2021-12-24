package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.DBConnector;
import model.FilmTextDTO;

public class FilmTextController {
    private Connection conn;

    public FilmTextController(DBConnector connector) {
        conn = connector.makeConnection();
    }

    public void insert(FilmTextDTO f) {
        String query = new String("INSERT INTO `film_text`(`film_id`,`title`,`description`)VALUES(?,?,?)");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, f.getFilmId());
            pstmt.setString(2, f.getTitle());
            pstmt.setString(3, f.getDescription());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(FilmTextDTO f) {
        String query = new String("UPDATE `film_text` SET `description`=? WHERE `film_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, f.getDescription());
            pstmt.setInt(2, f.getFilmId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public void delete(FilmTextDTO f) {
//        String query = new String("DELETE FROM `film_text` WHERE ``");
//        try {
//            PreparedStatement pstmt = conn.prepareStatement(query);
//            
//            pstmt.executeUpdate();
//            pstmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public void deleteByFilmId(int filmId) {
        String query = new String("DELETE FROM `film_text` WHERE `film_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, filmId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<FilmTextDTO> selectAll() {
        ArrayList<FilmTextDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `film_text`");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    FilmTextDTO f = new FilmTextDTO();
                    f.setFilmId(rs.getInt("film_id"));
                    f.setTitle(rs.getString("title"));
                    f.setDescription(rs.getString("description"));
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
    public ArrayList<FilmTextDTO> selectAllSearchByTitle(String titlePart) {
        ArrayList<FilmTextDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `film_text`WHERE`title`LIKE ?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%"+titlePart+"%");
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    FilmTextDTO f = new FilmTextDTO();
                    f.setFilmId(rs.getInt("film_id"));
                    f.setTitle(rs.getString("title"));
                    f.setDescription(rs.getString("description"));
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
    public ArrayList<FilmTextDTO> selectAllSearchByDescription(String descriptionPart) {
        ArrayList<FilmTextDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `film_text`WHERE`description`LIKE ?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%"+descriptionPart+"%");
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    FilmTextDTO f = new FilmTextDTO();
                    f.setFilmId(rs.getInt("film_id"));
                    f.setTitle(rs.getString("title"));
                    f.setDescription(rs.getString("description"));
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
}
