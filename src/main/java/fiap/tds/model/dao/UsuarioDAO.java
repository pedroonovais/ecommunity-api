package fiap.tds.model.dao;

import fiap.tds.model.vo.Usuario;
import fiap.tds.resource.UsuarioResource;
import fiap.tds.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Connection getConnection() throws SQLException {
        return new DatabaseConnection().getConnection();
    }

    public int insert(Usuario usuario) {
        int generatedId = 0;

        String sql = "INSERT INTO TB_USUARIO (NOME_USUARIO, EMAIL_USUARIO, QTD_PONTOS, ADMIN) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setInt(3, usuario.getPontos());

            String admin = usuario.isAdmin();
            if (!"S".equals(admin) && !"N".equals(admin)) {
                throw new SQLException("Valor inválido para o campo ADMIN. Esperado 'S' ou 'N'.");
            }
            stmt.setString(4, admin);

            stmt.executeUpdate();

            String sqlGetId = "SELECT MAX(ID_USUARIO) AS maior_id FROM TB_USUARIO";
            try (PreparedStatement stmtId = connection.prepareStatement(sqlGetId);
                 ResultSet rs = stmtId.executeQuery()) {
                if (rs.next()) {
                    generatedId = rs.getInt("maior_id");  // Recupera o valor do maior ID
                } else {
                    throw new SQLException("Falha ao obter o ID do usuário.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedId;
    }

    public Usuario select(int id) {
        String sql = "SELECT * FROM TB_USUARIO WHERE ID_USUARIO = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("ID_USUARIO"),
                        rs.getString("NOME_USUARIO"),
                        rs.getString("EMAIL_USUARIO"),
                        rs.getInt("QTD_PONTOS"),
                        rs.getString("ADMIN")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Usuario> selectAll() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM TB_USUARIO";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("ID_USUARIO"),
                        rs.getString("NOME_USUARIO"),
                        rs.getString("EMAIL_USUARIO"),
                        rs.getInt("QTD_PONTOS"),
                        rs.getString("ADMIN")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public boolean update(Usuario usuario) {
        String checkSql = "SELECT 1 FROM TB_USUARIO WHERE ID_USUARIO = ?";
        try (Connection connection = getConnection();
             PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {

            checkStmt.setInt(1, usuario.getId());
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                return false;
            }

            String sql = "UPDATE TB_USUARIO SET NOME_USUARIO = ?, EMAIL_USUARIO = ?, QTD_PONTOS = ?, ADMIN = ? WHERE ID_USUARIO = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, usuario.getNome());
                stmt.setString(2, usuario.getEmail());
                stmt.setInt(3, usuario.getPontos());
                stmt.setString(4, usuario.isAdmin());
                stmt.setInt(5, usuario.getId());

                stmt.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean delete(int id) {
        String sql = "DELETE FROM TB_USUARIO WHERE ID_USUARIO = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario login(String login, String senha) {
        String sql = "SELECT *\n" +
                "FROM TB_USUARIO U\n" +
                "INNER JOIN TB_LOGIN_USUARIO LU\n" +
                "ON U.ID_USUARIO = LU.ID_USUARIO\n" +
                "WHERE LOGIN_USUARIO = ?\n" +
                "AND SENHA = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                    rs.getInt("ID_USUARIO"),
                    rs.getString("NOME_USUARIO"),
                    rs.getString("EMAIL_USUARIO"),
                    rs.getInt("QTD_PONTOS"),
                    rs.getString("ADMIN")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
