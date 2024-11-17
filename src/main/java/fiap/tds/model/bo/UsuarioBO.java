package fiap.tds.model.bo;

import fiap.tds.model.vo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Objects;

public class UsuarioBO {

    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public boolean verificaUsuarioAdmin(Usuario usuario){
        return Objects.equals(usuario.isAdmin(), "S");
    }

    public String gerarTokenLogin(Usuario usuario) {
        return Jwts.builder()
                .setSubject(String.valueOf(usuario.getId()))
                .claim("nome", usuario.getNome())
                .claim("email", usuario.getEmail())
                .claim("qtdPontos", usuario.getPontos())
                .claim("admin", usuario.isAdmin())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .signWith(KEY)
                .compact();
    }

}
