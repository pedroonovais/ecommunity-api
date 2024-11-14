package fiap.tds.model.dao;

import fiap.tds.model.vo.Local;
import fiap.tds.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocalDAO {
    private Connection getConnection() throws SQLException {
        return new DatabaseConnection().getConnection();
    }

    public int insert(Local local) {
        int generatedId = 0;

        String sqlInsert = "INSERT INTO tb_local (nome_local, categoria, logradouro_numero_local, cep_local, cidade_local, estado_local, latitude_local, longitude_local) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {

            stmt.setString(1, local.getNome());
            stmt.setString(2, local.getCategoria());
            stmt.setString(3, local.getLogradouro());
            stmt.setString(4, String.valueOf(local.getCep()));
            stmt.setString(5, local.getCidade());
            stmt.setString(6, local.getEstado());
            stmt.setDouble(7, Double.parseDouble(String.valueOf(local.getLatitude())));
            stmt.setDouble(8, Double.parseDouble(String.valueOf(local.getLongitude())));

            stmt.executeUpdate();

            String sqlGetId = "SELECT MAX(id_local) AS maior_id FROM tb_local";
            try (PreparedStatement stmtId = conn.prepareStatement(sqlGetId);
                 ResultSet rs = stmtId.executeQuery()) {

                if (rs.next()) {
                    generatedId = rs.getInt("maior_id");
                } else {
                    throw new SQLException("Falha ao obter ID do local.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir o registro: " + e.getMessage());
        }
        return generatedId;
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

    public boolean update(Local local) {
        String sql = "UPDATE tb_local SET " +
                "nome_local = ?, categoria = ?, logradouro_numero_local = ?, cep_local = ?, " +
                "cidade_local = ?, estado_local = ?, latitude_local = ?, longitude_local = ? " +
                "WHERE id_local = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, local.getNome());
            stmt.setString(2, local.getCategoria());
            stmt.setString(3, local.getLogradouro());
            stmt.setString(4, String.valueOf(local.getCep()));
            stmt.setString(5, local.getCidade());
            stmt.setString(6, local.getEstado());
            stmt.setDouble(7, Double.parseDouble(String.valueOf(local.getLatitude())));
            stmt.setDouble(8, Double.parseDouble(String.valueOf(local.getLongitude())));
            stmt.setInt(9, local.getId());
            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o registro: " + e.getMessage());
            return false;
        }
    }

    public List<Local> selectAll() {
        String sql = "SELECT * FROM TB_LOCAL";
        List<Local> locais = new ArrayList<>();

        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
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
                locais.add(local);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return locais;
    }
}