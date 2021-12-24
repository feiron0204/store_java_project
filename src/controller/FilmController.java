package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.DBConnector;
import model.FilmDTO;

public class FilmController {
    private Connection conn;

    public FilmController(DBConnector connector) {
        conn = connector.makeConnection();
    }

    public int insert(FilmDTO f) {
        String query = new String(
                "INSERT INTO `film`(`title`,`description`,`release_year`,`language_id`,`original_language_id`,`rental_duration`,`rental_rate`,`length`,`replacement_cost`,`rating`,`special_features`,`last_update`)VALUES(?,?,?,?,?,?,?,?,?,?,?,NOW())");
        int filmId=0;
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, f.getTitle());
            pstmt.setString(2, f.getDescription());
            pstmt.setInt(3, f.getReleaseYear());
            pstmt.setInt(4, f.getLanguageId());
            pstmt.setInt(5, f.getOriginalLanguageId());
            pstmt.setInt(6, f.getRentalDuration());
            pstmt.setDouble(7, f.getRentalRate());
            pstmt.setInt(8, f.getLength());
            pstmt.setDouble(9, f.getReplacementCost());
            pstmt.setString(10, f.getRating());
            pstmt.setString(11, f.getSpecialFeatures());
            pstmt.executeUpdate();
            
            query = new String ("SELECT LAST_INSERT_ID()");
            pstmt=conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            
            rs.next();
            filmId= rs.getInt("LAST_INSERT_ID()");
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filmId;
    }

    public void update(FilmDTO f) {
        String query = new String(
                "UPDATE `film` SET `description`=?,`release_year`=?,`language_id`=?,`original_language_id`=?,`rental_duation`=?,`rental_rate`=?,`replacement_cost`=?,`special_features`=?,`last_update`=NOW() WHERE `film_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, f.getDescription());
            pstmt.setInt(2, f.getReleaseYear());
            pstmt.setInt(3, f.getLanguageId());
            pstmt.setInt(4, f.getOriginalLanguageId());
            pstmt.setInt(5, f.getRentalDuration());
            pstmt.setDouble(6, f.getRentalRate());
            pstmt.setDouble(7, f.getReplacementCost());
            pstmt.setString(8, f.getSpecialFeatures());
            pstmt.setInt(9, f.getFilmId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(FilmDTO f) {
        String query = new String("DELETE FROM `film` WHERE `film_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, f.getFilmId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByLanguageId(int languageId) {
        String query = new String("DELETE FROM `film` WHERE `language_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, languageId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByOriginalLanguageId(int OriginalLanguageId) {
        String query = new String("DELETE FROM `film` WHERE `original_language_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, OriginalLanguageId);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<FilmDTO> selectAll() {
        ArrayList<FilmDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `film`");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    FilmDTO f = new FilmDTO();
                    f.setFilmId(rs.getInt("film_id"));
                    f.setTitle(rs.getString("title"));
                    f.setDescription(rs.getString("description"));
                    f.setReleaseYear(rs.getInt("release_year"));
                    f.setLanguageId(rs.getInt("language_id"));
                    f.setOriginalLanguageId(rs.getInt("original_language_id"));
                    f.setRentalDuration(rs.getInt("rental_duration"));
                    f.setRentalRate(rs.getDouble("rental_rate"));
                    f.setLength(rs.getInt("length"));
                    f.setReplacementCost(rs.getDouble("replacement_cost"));
                    f.setRating(rs.getString("rating"));
                    f.setSpecialFeatures(rs.getString("special_features"));
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

    public ArrayList<FilmDTO> selectAllByLanguageId(int languageId) {
        ArrayList<FilmDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `film` WHERE `language_id`=?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, languageId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    FilmDTO f = new FilmDTO();
                    f.setFilmId(rs.getInt("film_id"));
                    f.setTitle(rs.getString("title"));
                    f.setDescription(rs.getString("description"));
                    f.setReleaseYear(rs.getInt("release_year"));
                    f.setLanguageId(rs.getInt("language_id"));
                    f.setOriginalLanguageId(rs.getInt("original_language_id"));
                    f.setRentalDuration(rs.getInt("rental_duration"));
                    f.setRentalRate(rs.getDouble("rental_rate"));
                    f.setLength(rs.getInt("length"));
                    f.setReplacementCost(rs.getDouble("replacement_cost"));
                    f.setRating(rs.getString("rating"));
                    f.setSpecialFeatures(rs.getString("special_features"));
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

    public ArrayList<FilmDTO> selectAllSearchByTitle(String titlePart) {
        ArrayList<FilmDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `film`WHERE `title`LIKE ?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + titlePart + "%");
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    FilmDTO f = new FilmDTO();
                    f.setFilmId(rs.getInt("film_id"));
                    f.setTitle(rs.getString("title"));
                    f.setDescription(rs.getString("description"));
                    f.setReleaseYear(rs.getInt("release_year"));
                    f.setLanguageId(rs.getInt("language_id"));
                    f.setOriginalLanguageId(rs.getInt("original_language_id"));
                    f.setRentalDuration(rs.getInt("rental_duration"));
                    f.setRentalRate(rs.getDouble("rental_rate"));
                    f.setLength(rs.getInt("length"));
                    f.setReplacementCost(rs.getDouble("replacement_cost"));
                    f.setRating(rs.getString("rating"));
                    f.setSpecialFeatures(rs.getString("special_features"));
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

    public ArrayList<FilmDTO> selectAllSearchByDescription(String descriptionPart) {
        ArrayList<FilmDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `film`WHERE `description`LIKE ?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + descriptionPart + "%");
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    FilmDTO f = new FilmDTO();
                    f.setFilmId(rs.getInt("film_id"));
                    f.setTitle(rs.getString("title"));
                    f.setDescription(rs.getString("description"));
                    f.setReleaseYear(rs.getInt("release_year"));
                    f.setLanguageId(rs.getInt("language_id"));
                    f.setOriginalLanguageId(rs.getInt("original_language_id"));
                    f.setRentalDuration(rs.getInt("rental_duration"));
                    f.setRentalRate(rs.getDouble("rental_rate"));
                    f.setLength(rs.getInt("length"));
                    f.setReplacementCost(rs.getDouble("replacement_cost"));
                    f.setRating(rs.getString("rating"));
                    f.setSpecialFeatures(rs.getString("special_features"));
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

    public ArrayList<FilmDTO> selectAllSearchByReleaseYear(int releaseYear) {
        ArrayList<FilmDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `film`WHERE `release_year`= ?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, releaseYear);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    FilmDTO f = new FilmDTO();
                    f.setFilmId(rs.getInt("film_id"));
                    f.setTitle(rs.getString("title"));
                    f.setDescription(rs.getString("description"));
                    f.setReleaseYear(rs.getInt("release_year"));
                    f.setLanguageId(rs.getInt("language_id"));
                    f.setOriginalLanguageId(rs.getInt("original_language_id"));
                    f.setRentalDuration(rs.getInt("rental_duration"));
                    f.setRentalRate(rs.getDouble("rental_rate"));
                    f.setLength(rs.getInt("length"));
                    f.setReplacementCost(rs.getDouble("replacement_cost"));
                    f.setRating(rs.getString("rating"));
                    f.setSpecialFeatures(rs.getString("special_features"));
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

    public ArrayList<FilmDTO> selectAllSearchByRating(String rating) {
        ArrayList<FilmDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `film`WHERE `rating`= ?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, rating);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    FilmDTO f = new FilmDTO();
                    f.setFilmId(rs.getInt("film_id"));
                    f.setTitle(rs.getString("title"));
                    f.setDescription(rs.getString("description"));
                    f.setReleaseYear(rs.getInt("release_year"));
                    f.setLanguageId(rs.getInt("language_id"));
                    f.setOriginalLanguageId(rs.getInt("original_language_id"));
                    f.setRentalDuration(rs.getInt("rental_duration"));
                    f.setRentalRate(rs.getDouble("rental_rate"));
                    f.setLength(rs.getInt("length"));
                    f.setReplacementCost(rs.getDouble("replacement_cost"));
                    f.setRating(rs.getString("rating"));
                    f.setSpecialFeatures(rs.getString("special_features"));
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

    public ArrayList<FilmDTO> selectAllSearchBySpecialFeatures(String specialFeatures) {
        ArrayList<FilmDTO> list = new ArrayList<>();
        String query = new String("SELECT * FROM `film`WHERE `spectial_features`LIKE ?");
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + specialFeatures + "%");
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    FilmDTO f = new FilmDTO();
                    f.setFilmId(rs.getInt("film_id"));
                    f.setTitle(rs.getString("title"));
                    f.setDescription(rs.getString("description"));
                    f.setReleaseYear(rs.getInt("release_year"));
                    f.setLanguageId(rs.getInt("language_id"));
                    f.setOriginalLanguageId(rs.getInt("original_language_id"));
                    f.setRentalDuration(rs.getInt("rental_duration"));
                    f.setRentalRate(rs.getDouble("rental_rate"));
                    f.setLength(rs.getInt("length"));
                    f.setReplacementCost(rs.getDouble("replacement_cost"));
                    f.setRating(rs.getString("rating"));
                    f.setSpecialFeatures(rs.getString("special_features"));
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

    public FilmDTO selectOne(int filmId) {
        String query = new String("SELECT * FROM `film`WHERE `film_id`= ?");
        FilmDTO f = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, filmId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {

            } else {
                f = new FilmDTO();
                f.setFilmId(rs.getInt("film_id"));
                f.setTitle(rs.getString("title"));
                f.setDescription(rs.getString("description"));
                f.setReleaseYear(rs.getInt("release_year"));
                f.setLanguageId(rs.getInt("language_id"));
                f.setOriginalLanguageId(rs.getInt("original_language_id"));
                f.setRentalDuration(rs.getInt("rental_duration"));
                f.setRentalRate(rs.getDouble("rental_rate"));
                f.setLength(rs.getInt("length"));
                f.setReplacementCost(rs.getDouble("replacement_cost"));
                f.setRating(rs.getString("rating"));
                f.setSpecialFeatures(rs.getString("special_features"));
                f.setLastUpdate(rs.getTimestamp("last_update"));
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return f;
    }
}
