package fiap.tds.model.dao;

import fiap.tds.model.vo.Local;
import fiap.tds.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocalDAO {
    private Connection getConnection() throws SQLException {
        return new DatabaseConnection().getConnection();
    }

    public int insert(Local local) {
        int idLocal = -1;
        String sql = "INSERT INTO tb_local " +
                "(nome_local, categoria, logradouro_numero_local, cep_local, " +
                "cidade_local, estado_local, latitude_local, longitude_local) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Definir os valores do PreparedStatement
            stmt.setString(1, local.getNome());
            stmt.setString(2, local.getCategoria());
            stmt.setString(3, local.getLogradouro());
            stmt.setString(4, local.getCep());
            stmt.setString(5, local.getCidade());
            stmt.setString(6, local.getEstado());
            stmt.setDouble(7, local.getLatitude());  // Latitude como double
            stmt.setDouble(8, local.getLongitude()); // Longitude como double

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idLocal = rs.getInt(1);  // O primeiro índice é o id gerado
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return idLocal;
    }

    public Local select(int id) {
        String sql = "SELECT * FROM TB_LOCAL WHERE id_local = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Local local = new Local();
                local.setId(rs.getInt("id_local"));
                local.setNome(rs.getString("nome_local"));
                local.setCategoria(rs.getString("categoria"));
                local.setLogradouro(rs.getString("logradouro_numero_local"));
                local.setCep(rs.getString("cep_local"));
                local.setCidade(rs.getString("cidade_local"));
                local.setEstado(rs.getString("estado_local"));
                local.setLatitude(rs.getFloat("latitude_local"));
                local.setLongitude(rs.getFloat("longitude_local"));
                return local;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Retorna null caso nenhum registro seja encontrado
    }


    public boolean deletar(int id) {
        String sql = "DELETE FROM TB_LOCAL WHERE id_local = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
