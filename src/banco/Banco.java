/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.util.ArrayList;
import java.util.List;
import negocio.Alocacao;
import negocio.Atividade;
import negocio.Usuario;
import negocio.Recurso;

/**
 *
 * @author Leandro Martins
 */
public class Banco {
    private int recursosQnt;
    private List <Usuario> usuarios;
    private List <Recurso> recursos;
    private List <Alocacao> alocacoes;
    private List <Atividade> atividades;
    
    public Banco() {
        recursosQnt = 0;
        usuarios = new ArrayList <Usuario>();
        recursos = new ArrayList <Recurso>();
        alocacoes = new ArrayList <Alocacao>();
        atividades = new ArrayList <Atividade>();
    }
    
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }

    public int getRecursosQnt() {
        return recursosQnt;
    }

    public void setRecursosQnt(int recursosQnt) {
        this.recursosQnt = recursosQnt;
    }

    public List<Alocacao> getAlocacoes() {
        return alocacoes;
    }

    public void setAlocacoes(List<Alocacao> alocacoes) {
        this.alocacoes = alocacoes;
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }
    
    public void cadastrarUsuario(Usuario user) {
        int existe = 0;
        for (Usuario usuario : usuarios) {
            if(usuario.getEmail().equals(user.getEmail())) {
                existe = 1;
                break;
            }
        }
        
        if(existe == 0) {
            usuarios.add(user);
        }
    }
    
    public void printUsuarios() {
        for (Usuario usuario : usuarios) {
            System.out.println("Nome:" + usuario.getNome() + ". Cargo: " + usuario.getCargo());
        }
    }
    
    public void printRecursos() {
        for (Recurso recurso : recursos) {
            System.out.println("Id:" + recurso.getId() + ". Tipo: " + recurso.getTipo() + ". Responsável: " + recurso.getResponsavel().getNome() + ". Status: " + recurso.getStatus());
        }
    }
    
    public void cadastrarRecurso(String tipo, Usuario user) {
        Recurso recurso = new Recurso(recursosQnt, tipo, user, "Disponível");
        recursos.add(recurso);
        recursosQnt++;
    }
    
    public Usuario autenticar(String email, String senha) {
        int encontrado = 0;
        Usuario user = null;
        for (Usuario usuario : usuarios) {
            if((usuario.getEmail().equals(email)) && (usuario.getSenha().equals(senha))) {
                encontrado++;
                user = usuario;
                break;
            }
        }
        
        return user;
    }
    
    public void cadastraAlocacao(Recurso rec, Usuario user) {

        
        Usuario nUser = new Usuario(user.getNome(), user.getEmail(), user.getSenha(), user.getCargo());
        Recurso nRec = new Recurso(rec.getId(), rec.getTipo(), rec.getResponsavel(), rec.getStatus(), rec.getDataInicio(), rec.getDataFim(), null);
    
        Alocacao nAl = new Alocacao();
        nAl.setRec(nRec);
        nAl.setUser(nUser);
        alocacoes.add(nAl);
    }

    public void iniciarAlocacao(int id) {
        for (Recurso recurso : recursos) {
            if(recurso.getId() == id) {
                recurso.setStatus("Alocado");
                break;
            }
        }
        
        for (Alocacao al : alocacoes) {
            if(al.getRec().getId() == id) {
                al.getRec().setStatus("Alocado");
                break;
            }
        }
    }

    public void confirmarAlocacao(int id) {
        for (Recurso recurso : recursos) {
            if(recurso.getId() == id) {
                recurso.setStatus("Em andamento");
                recurso.getResponsavel().setLivre(0);
                break;
            }
        }
        
        for (Alocacao al : alocacoes) {
            if(al.getRec().getId() == id) {
                al.getRec().setStatus("Em andamento");
                al.getRec().getResponsavel().setLivre(0);
                break;
            }
        }
    }

    public void concluirAlocacao(int id, Atividade ativ) {
        System.out.println("Aqui: " + ativ.getTipo());
        for (Recurso recurso : recursos) {
            if(recurso.getId() == id) {
                recurso.setStatus("Disponível");
                recurso.getResponsavel().setLivre(1);
                break;
            }
        }
        
        for (Alocacao al : alocacoes) {
            if(al.getRec().getId() == id) {
                al.getRec().setStatus("Concluído");
                al.getRec().getResponsavel().setLivre(1);
                al.setAtiv(ativ);
                break;
            }
        }
    }
}
