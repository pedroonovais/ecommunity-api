package fiap.tds.model.vo;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private int pontos;
    private boolean admin;

    public Usuario(){}

    public Usuario(int id, String nome, String email, int pontos, boolean admin) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.pontos = pontos;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", pontos=" + pontos +
                ", admin=" + admin +
                '}';
    }
}
