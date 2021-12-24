package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.DBConnector;
import model.CategoryDTO;

public class CategoryController {
private Connection conn;
    
    public CategoryController(DBConnector connector) {
        conn = connector.makeConnection();
    }
    
    public void insert(CategoryDTO c) {
        String query=new String("INSERT INTO `category`(`name`,`last_update`)VALUES(?,NOW())");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, c.getName());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public void delete(CategoryDTO c) {
        String query = new String("DELETE FROM `category`WHERE `category_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, c.getCategoryId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    public CategoryDTO selectOne(int categoryId) {
        CategoryDTO c = null;
        String query = new String("SELECT * FROM `category` WHERE `category_id`=?");
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    c = new CategoryDTO();
                    c.setCategoryId(rs.getInt("category_id"));
                    c.setName(rs.getString("name"));
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
    
    public ArrayList<CategoryDTO> selectAll(){
        ArrayList<CategoryDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `category`");
        try {
            PreparedStatement pstmt=conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            if(!rs.next()) {
                
            }else {
                rs=pstmt.executeQuery();
                while(rs.next()) {
                    CategoryDTO c=new CategoryDTO();
                    c.setCategoryId(rs.getInt("category_id"));
                    c.setName(rs.getString("name"));
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
    public ArrayList<CategoryDTO> selectAll(String namePart) {
        ArrayList<CategoryDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `category` WHERE `name` LIKE ?");

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + namePart + "%");
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    CategoryDTO c = new CategoryDTO();
                    c.setCategoryId(rs.getInt("category_id"));
                    c.setName(rs.getString("name"));
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
}
