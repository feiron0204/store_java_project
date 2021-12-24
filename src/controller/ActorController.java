package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.DBConnector;
import model.ActorDTO;

public class ActorController {
    private Connection conn;

    public ActorController(DBConnector connector) {
        conn = connector.makeConnection();

    }

    public void insert(ActorDTO a) {
        String query = new String("INSERT INTO `actor`(`first_name`,`last_name`,`last_update`)VALUES(?,?,NOW())");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, a.getFirstName());
            pstmt.setString(2, a.getLastName());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(ActorDTO a) {
        String query = new String(
                "UPDATE `actor` SET `first_name`=?,`last_name`=? ,`last_update`=NOW() WHERE`actor_id`=?");
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, a.getFirstName());
            pstmt.setString(2, a.getLastName());
            pstmt.setInt(3, a.getActorId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(ActorDTO a) {
        String query = new String("DELETE FROM `actor`WHERE `actor_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, a.getActorId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ActorDTO> selectAll() {
        ArrayList<ActorDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `actor`");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    ActorDTO a = new ActorDTO();
                    a.setActorId(rs.getInt("actor_id"));
                    a.setFirstName(rs.getString("first_name"));
                    a.setLastName(rs.getString("last_name"));
                    
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

    public ActorDTO selectOne(int actorId) {
        ActorDTO a = null;
        String query = new String("SELECT * FROM `actor` WHERE `actor_id`=?");
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, actorId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    a = new ActorDTO();
                    a.setActorId(rs.getInt("actor_id"));
                    a.setFirstName(rs.getString("first_name"));
                    a.setLastName(rs.getString("last_name"));
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

    public ArrayList<ActorDTO> selectAll(String namePart) {
        ArrayList<ActorDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `actor` WHERE `first_name`LIKE ? OR `last_name` LIKE ?");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + namePart + "%");
            pstmt.setString(2, "%" + namePart + "%");
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    ActorDTO a = new ActorDTO();
                    a.setActorId(rs.getInt("actor_id"));
                    a.setFirstName(rs.getString("first_name"));
                    a.setLastName(rs.getString("last_name"));
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

//    public String findActorNameById(int actorId) {
//        String name = null;
//        String query = new String("SELECT * FROM `actor` WHERE `actor_id` = ?");
//
//        try {
//            PreparedStatement pstmt = conn.prepareStatement(query);
//            pstmt.setInt(1, actorId);
//            ResultSet rs = pstmt.executeQuery();
//            if (!rs.next()) {
//                name = new String("삭제된 배우");
//            } else {
//                name = new String(rs.getString("first_name") + " " + rs.getString("last_name"));
//            }
//            rs.close();
//            pstmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return name;
//    }
}
