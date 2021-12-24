package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.DBConnector;
import model.FilmActorDTO;

public class FilmActorController {
private Connection conn;
    
    public FilmActorController(DBConnector connector) {
        conn = connector.makeConnection();
    }
    public void insert(FilmActorDTO f) {
        String query = new String("INSERT INTO `film_actor`(`actor_id`,`film_id`,`last_update`)VALUES(?,?,NOW())");
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, f.getActorId());
            pstmt.setInt(2, f.getFilmId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("이미 추가된 정보입니다.");
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
    public void delete(FilmActorDTO f) {
        String query = new String("DELETE FROM `film_actor` WHERE `film_id`=? AND `actor_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, f.getFilmId());
            pstmt.setInt(2, f.getActorId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteByActorId(int actorId) {
        String query = new String("DELETE FROM `film_actor` WHERE `actor_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, actorId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteByFilmId(int filmId) {
        String query = new String("DELETE FROM `film_actor` WHERE `film_id`=? ");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, filmId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<FilmActorDTO> selectAll() {
        ArrayList<FilmActorDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `film_actor` ");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    FilmActorDTO f = new FilmActorDTO();
                    f.setActorId(rs.getInt("actor_id"));
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
    public ArrayList<FilmActorDTO> selectAllByActorId(int actorId) {
        ArrayList<FilmActorDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `film_actor`WHERE `actor_id`=? ");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, actorId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    FilmActorDTO f = new FilmActorDTO();
                    f.setActorId(rs.getInt("actor_id"));
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
    
    public ArrayList<FilmActorDTO> selectAllByFilmId(int filmId) {
        ArrayList<FilmActorDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `film_actor`WHERE`film_id`=? ");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, filmId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    FilmActorDTO f = new FilmActorDTO();
                    f.setActorId(rs.getInt("actor_id"));
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
    public FilmActorDTO selectOne(int actorId,int filmId) {
        FilmActorDTO f=null;
        String query = new String("SELECT * FROM `film_actor`WHERE `film_id`=? AND `actor_id`=? ");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, filmId);
            pstmt.setInt(2, actorId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    f = new FilmActorDTO();
                    f.setActorId(rs.getInt("actor_id"));
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
