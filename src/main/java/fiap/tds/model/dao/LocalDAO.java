package fiap.tds.model.dao;

import fiap.tds.model.vo.Local;
import fiap.tds.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class LocalDAO {
    private Connection getConnection() throws SQLException {
        return new DatabaseConnection().getConnection();
    }

    public boolean insert(Local local){
        String sql = "INSERT INTO TB_LOCAL (id_local, )";
        return false;
    }
}
