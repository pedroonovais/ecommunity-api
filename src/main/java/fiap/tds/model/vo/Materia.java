package fiap.tds.model.vo;

import java.time.LocalDateTime;

public class Materia {
    private int idMateria;
    private int idUsuario;
    private String tituloMateria;
    private String textoMateria;
    private String ativo;
    private LocalDateTime dtCriacao;
    private LocalDateTime dtUpdate;

    // Construtor padrão
    public Materia() {}

    // Construtor com parâmetros
    public Materia(int idMateria, int idUsuario, String tituloMateria, String textoMateria, String ativo, LocalDateTime dtCriacao, LocalDateTime dtUpdate) {
        this.idMateria = idMateria;
        this.idUsuario = idUsuario;
        this.tituloMateria = tituloMateria;
        this.textoMateria = textoMateria;
        this.ativo = ativo;
        this.dtCriacao = dtCriacao;
        this.dtUpdate = dtUpdate;
    }

    // Getters e Setters
    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTituloMateria() {
        return tituloMateria;
    }

    public void setTituloMateria(String tituloMateria) {
        this.tituloMateria = tituloMateria;
    }

    public String getTextoMateria() {
        return textoMateria;
    }

    public void setTextoMateria(String textoMateria) {
        this.textoMateria = textoMateria;
    }

    public String isAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(LocalDateTime dtCriacao) {
        this.dtCriacao = dtCriacao;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    @Override
    public String toString() {
        return "Materia{" +
                "idMateria=" + idMateria +
                ", idUsuario=" + idUsuario +
                ", tituloMateria='" + tituloMateria + '\'' +
                ", textoMateria='" + textoMateria + '\'' +
                ", ativo=" + ativo +
                ", dtCriacao=" + dtCriacao +
                ", dtUpdate=" + dtUpdate +
                '}';
    }
}
