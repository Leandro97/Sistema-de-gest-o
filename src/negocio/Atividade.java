package negocio;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leandro Martins
 */
public class Atividade {
    private String tipo;
    private String titulo;
    private String descricao;
    private List <Usuario> participantes;
    private String material;

    public Atividade(String tipo, String titulo, String descricao, List<Usuario> participantes, String material) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.descricao = descricao;
        this.participantes = participantes;
        this.material = material;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Usuario> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Usuario> participantes) {
        this.participantes = participantes;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
}
