package abstracts;


import interfaces.GenericDB;
import services.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDBService<T> implements GenericDB<T> {

    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;
    protected abstract String getInsertQuery();
    protected abstract void setInsertQueryParameters(PreparedStatement pstmt, T entity) throws SQLException;
    protected abstract String getSelectQuery();

    @Override
    public void create(T entity) {
        String query = getInsertQuery();

        try (Connection conn = ConnectionDB.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            setInsertQueryParameters(pstmt, entity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T read(int id) {
        String query = getSelectQuery();

        try (Connection conn = ConnectionDB.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
