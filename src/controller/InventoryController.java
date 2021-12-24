package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.DBConnector;
import model.InventoryDTO;

public class InventoryController {
    private Connection conn;

    public InventoryController(DBConnector connector) {
        conn = connector.makeConnection();
    }

    public void insert(InventoryDTO i) {
        String query = new String("INSERT INTO `inventory`(`film_id`,`store_id`,`last_update`)VALUES(?,?,NOW())");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, i.getFilmId());
            pstmt.setInt(2, i.getStoreId());

            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(InventoryDTO i) {
        String query = new String("UPDATE `inventory` SET `store_id`=?,`last_update`=NOW() WHERE `inventory_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, i.getStoreId());
            pstmt.setInt(2, i.getInventoryId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(InventoryDTO i) {
        String query = new String("DELETE FROM `inventory` WHERE `inventory_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, i.getInventoryId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByFilmId(int filmId) {
        String query = new String("DELETE FROM `inventory` WHERE `film_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, filmId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByStoreId(int storeId) {
        String query = new String("DELETE FROM `inventory` WHERE `store_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, storeId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<InventoryDTO> selectAll(int storeId) {
        ArrayList<InventoryDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM sakila.inventory left join `rental` on `inventory`.`inventory_id`=`rental`.`inventory_id` where `store_id`=? AND `inventory`.`inventory_id` not in (SELECT `inventory`.`inventory_id` FROM sakila.inventory inner join `rental` on `inventory`.`inventory_id`=`rental`.`inventory_id` where `return_date` is null AND `rental_date` is not null group by `inventory`.`inventory_id`) group by `inventory`.`inventory_id` order by `inventory`.`inventory_id`");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, storeId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    InventoryDTO i = new InventoryDTO();
                    i.setInventoryId(rs.getInt("inventory_id"));
                    i.setFilmId(rs.getInt("film_id"));
                    i.setStoreId(rs.getInt("store_id"));
                    i.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(i);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<InventoryDTO> selectAllByFilmId(int filmId,int storeId) {
        ArrayList<InventoryDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM sakila.inventory left join `rental` on `inventory`.`inventory_id`=`rental`.`inventory_id` where `store_id`=? AND `film_id`=? AND `inventory`.`inventory_id` not in (SELECT `inventory`.`inventory_id` FROM sakila.inventory inner join `rental` on `inventory`.`inventory_id`=`rental`.`inventory_id` where `film_id`=? AND `return_date` is null AND`rental_date` is not null group by `inventory`.`inventory_id`) group by `inventory`.`inventory_id` order by `inventory`.`inventory_id`");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, storeId);
            pstmt.setInt(2, filmId);
            pstmt.setInt(3, filmId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    InventoryDTO i = new InventoryDTO();
                    i.setInventoryId(rs.getInt("inventory_id"));
                    i.setFilmId(rs.getInt("film_id"));
                    i.setStoreId(rs.getInt("store_id"));
                    i.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(i);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<InventoryDTO> selectAllByFilmId2(int filmId,int storeId) {
        ArrayList<InventoryDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `inventory` where `store_id`=? AND `film_id`=? group by `inventory`.`inventory_id`");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, storeId);
            pstmt.setInt(2, filmId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    InventoryDTO i = new InventoryDTO();
                    i.setInventoryId(rs.getInt("inventory_id"));
                    i.setFilmId(rs.getInt("film_id"));
                    i.setStoreId(rs.getInt("store_id"));
                    i.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(i);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<InventoryDTO> selectAllByStoreId(int storeId) {
        ArrayList<InventoryDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `inventory`WHERE `store_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, storeId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    InventoryDTO i = new InventoryDTO();
                    i.setInventoryId(rs.getInt("inventory_id"));
                    i.setFilmId(rs.getInt("film_id"));
                    i.setStoreId(rs.getInt("store_id"));
                    i.setLastUpdate(rs.getTimestamp("last_update"));
                    list.add(i);
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public InventoryDTO selectOne(int inventoryId) {
        InventoryDTO i = null;
        String query = new String("SELECT * FROM `inventory`WHERE `inventory_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, inventoryId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                i = new InventoryDTO();
                i.setInventoryId(rs.getInt("inventory_id"));
                i.setFilmId(rs.getInt("film_id"));
                i.setStoreId(rs.getInt("store_id"));
                i.setLastUpdate(rs.getTimestamp("last_update"));

            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
}
