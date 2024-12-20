package fiap.tds.util;

import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;

public class DatabaseConnection {
    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";
    private static final String USER = "rm555276";
    private static final String PASSWORD = "160205";
    
    private Connection conn = null;

    public DatabaseConnection() throws SQLException {
        try {
            OracleDataSource ods = new OracleDataSource();
            ods.setURL(URL);
            ods.setUser(USER);
            ods.setPassword(PASSWORD);
            this.conn = ods.getConnection();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            throw e;
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    public void closeConnection() {
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
