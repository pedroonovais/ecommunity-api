package fiap.tds.model.dao;

import fiap.tds.model.vo.Materia;
import fiap.tds.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MateriaDAO {

    private Connection getConnection() throws SQLException {
        return new DatabaseConnection().getConnection();
    }

    public int insert(Materia materia) {
        int generatedId = 0;

        String sql = "INSERT INTO TB_MATERIA (ID_USUARIO, TITULO_MATERIA, TEXTO_MATERIA, ATIVO, DT_CRIACAO, DT_UPDATE) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, materia.getIdUsuario());
            stmt.setString(2, materia.getTituloMateria());
            stmt.setString(3, materia.getTextoMateria());
            stmt.setString(4, materia.isAtivo());
            stmt.setTimestamp(5, Timestamp.valueOf(materia.getDtCriacao()));
            stmt.setTimestamp(6, Timestamp.valueOf(materia.getDtUpdate()));

            // Executa o insert
            stmt.executeUpdate();

            // Obtém o ID gerado
            String sqlGetId = "SELECT MAX(ID_MATERIA) AS maior_id FROM TB_MATERIA";
            try (PreparedStatement stmtId = connection.prepareStatement(sqlGetId);
                 ResultSet rs = stmtId.executeQuery()) {
                if (rs.next()) {
                    generatedId = rs.getInt("maior_id");
                } else {
                    throw new SQLException("Falha ao obter o ID da matéria.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedId;
    }


    public Materia select(int id) {
        String sql = "SELECT * FROM TB_MATERIA WHERE ID_MATERIA = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Materia(
                    rs.getInt("ID_MATERIA"),
                    rs.getInt("ID_USUARIO"),
                    rs.getString("TITULO_MATERIA"),
                    rs.getString("TEXTO_MATERIA"),
                    rs.getString("ATIVO"),
                    rs.getTimestamp("DT_CRIACAO").toLocalDateTime(),
                    rs.getTimestamp("DT_UPDATE").toLocalDateTime(),
                    rs.getString("IMG_CAPA")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Materia> selectAll() {
        String sql = "SELECT * FROM TB_MATERIA";
        List<Materia> materias = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                materias.add(new Materia(
                    rs.getInt("ID_MATERIA"),
                    rs.getInt("ID_USUARIO"),
                    rs.getString("TITULO_MATERIA"),
                    rs.getString("TEXTO_MATERIA"),
                    rs.getString("ATIVO"),
                    rs.getTimestamp("DT_CRIACAO").toLocalDateTime(),
                    rs.getTimestamp("DT_UPDATE").toLocalDateTime(),
                        rs.getString("IMG_CAPA")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materias;
    }


    public boolean update(Materia materia) {
        String sql = "UPDATE TB_MATERIA SET TITULO_MATERIA = ?, TEXTO_MATERIA = ?, ATIVO = ?, DT_UPDATE = ? WHERE ID_MATERIA = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, materia.getTituloMateria());
            stmt.setString(2, materia.getTextoMateria());
            stmt.setString(3, materia.isAtivo());
            stmt.setTimestamp(4, Timestamp.valueOf(materia.getDtUpdate()));
            stmt.setInt(5, materia.getIdMateria());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deletar(int id) {
        String sql = "DELETE FROM TB_MATERIA WHERE ID_MATERIA = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
