package negocio;

import java.util.List;

/**
 * @author Leandro Martins
 */

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private String cargo;
    private int livre;

    public Usuario(String nome, String email, String senha, String cargo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cargo = cargo;
        livre = 1;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }  

    public int getLivre() {
        return livre;
    }

    public void setLivre(int livre) {
        this.livre = livre;
    }
    
    
}
