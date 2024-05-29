package services;


import abstracts.AbstractDBService;
import entities.author.Author;
import entities.category.Category;
import services.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CategoryService extends AbstractDBService<Category> {

    private static CategoryService instanta = null;

    private final ArrayList<Category> categorii = new ArrayList<Category>();

    // constructor privat
    private CategoryService() {

    }

    public static CategoryService getInstance() {
        if (instanta == null)
            instanta = new CategoryService();
        return instanta;
    }

    @Override
    protected Category mapResultSetToEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Category(id, name);
    }

    // Implementarea metodei abstracte din AbstractDBService pentru obținerea interogarii de inserare
    @Override
    protected String getInsertQuery() {
        return "INSERT INTO category(name) VALUES (?)";
    }

    // Implementarea metodei abstracte din AbstractDBService pentru setarea parametrilor interogarii de inserare
    @Override
    protected void setInsertQueryParameters(PreparedStatement pstmt, Category category) throws SQLException {
        pstmt.setString(1, category.getName());
    }

    // Implementarea metodei abstracte din AbstractDBService pentru obținerea interogarii de selectare
    @Override
    protected String getSelectQuery() {
        return "SELECT * FROM category WHERE id = ?";
    }
    public void addCategory(String name) {
        Category category = new Category(name);
        create(category);
    }

    public void displayCategories() {
        String selectSql = "SELECT * FROM category";

        Connection connection = ConnectionDB.getDatabaseConnection();

        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()) {
                System.out.println("Id:" + resultSet.getString(1));
                System.out.println("Name:" + resultSet.getString(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(String name) {
        String deleteBooksSql = "DELETE FROM Book WHERE category_id = (SELECT id FROM Category WHERE name = ?)";
        String deleteCategorySql = "DELETE FROM Category WHERE name = ?";

        Connection connection = ConnectionDB.getDatabaseConnection();

        try {
            // Start transaction
            connection.setAutoCommit(false);

            // Delete books by category
            try (PreparedStatement pstmtBooks = connection.prepareStatement(deleteBooksSql)) {
                pstmtBooks.setString(1, name);
                int booksDeleted = pstmtBooks.executeUpdate();
                System.out.println("Au fost șterse " + booksDeleted + " cărți asociate categoriei " + name + ".");
            }

            // Delete category
            try (PreparedStatement pstmtCategory = connection.prepareStatement(deleteCategorySql)) {
                pstmtCategory.setString(1, name);
                int rowsAffected = pstmtCategory.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Categoria cu numele " + name + " a fost ștearsă.");
                } else {
                    System.out.println("Categoria cu numele " + name + " nu a fost găsită.");
                }
            }

            // Commit transaction
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // Rollback transaction in case of error
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                // Restore auto-commit mode
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    private static Integer getCategoryIdByName(String currentName) {
        String query = "SELECT id FROM category WHERE name = ?";
        try (Connection conn = ConnectionDB.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, currentName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void updateCategoryName(String currentName, String newName) {
        Integer categoryId = getCategoryIdByName(currentName);
        if (categoryId != null) {
            String query = "UPDATE category SET name = ? WHERE id = ?";
            try (Connection conn = ConnectionDB.getDatabaseConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, newName);
                pstmt.setInt(2, categoryId);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Categoria a fost actualizată cu succes.");
                } else {
                    System.out.println("Categoria nu a putut fi actualizată.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Categoria cu numele '" + currentName + "' nu a fost găsită.");
        }
    }

    public Category getCategoryByName(String currentName) {
        String query = "SELECT * FROM category WHERE name = ?";
        try (Connection conn = ConnectionDB.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, currentName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int categoryId = rs.getInt("id");
                String categoryName = rs.getString("name");
                return new Category(categoryId, categoryName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Category getCategoryById(int categoryId) {
        CategoryService categoryService = CategoryService.getInstance();
        return categoryService.read(categoryId);
    }

}
