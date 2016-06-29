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
            
            if(!user.getEmail().equals("admin")) {
                System.out.println("Usuário cadastrado com sucesso!");
            }
        } else {
            System.out.println("Usuário já existente!");
        }
    }
    
    public void printUsuarios() {
        for (Usuario usuario : usuarios) {
            System.out.println("Nome:" + usuario.getNome() + ". Cargo: " + usuario.getCargo());
        }
    }
    
    public void printRecursos() {
        for (Recurso recurso : recursos) {
            System.out.println("Id:" + recurso.getId() + ". Tipo: " + recurso.getTipo() + ". Status: " + recurso.getStatus());
        }
    }
    
    public void cadastrarRecurso(String tipo) {
        Recurso recurso = new Recurso(recursosQnt, tipo, null, "Disponível");
        recurso.setResponsavel(null);
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
        Atividade nAtiv = new Atividade();
        Alocacao nAl = new Alocacao();
        
        nAtiv.setTipo("nada");
        
        nAl.setRec(nRec);
        nAl.setUser(nUser);
        nAl.setAtiv(nAtiv);
        
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
            if(al.getRec().getId() == id && al.getRec().getStatus().equals("Em processo de alocação")) {
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
            if(al.getRec().getId() == id && al.getRec().getStatus().equals("Alocado")) {
                al.getRec().setStatus("Em andamento");
                al.getRec().getResponsavel().setLivre(0);
                break;
            }
        }
    }

    public void concluirAlocacao(int id, Atividade ativ) {
        for (Recurso recurso : recursos) {
            if(recurso.getId() == id) {
                recurso.setStatus("Disponível");
                recurso.getResponsavel().setLivre(1);
                recurso.setResponsavel(null);
                recurso.getAtividades().add(ativ);
                break;
            }
        }
        
        for (Alocacao al : alocacoes) {
            if(al.getRec().getId() == id && al.getRec().getStatus().equals("Em andamento")) {
                al.getRec().setStatus("Concluído");
                al.getRec().getResponsavel().setLivre(1);
                ativ.getParticipantes().add(al.getRec().getResponsavel());
                al.setAtiv(ativ);
                break;
            }
        }
    }
    
    boolean participou(Atividade ativ, String email) {
        boolean p = false;
        
        if(ativ.getTipo().equals("nada")) return p;
        
        for (Usuario usuario : ativ.getParticipantes()) {
            if(usuario.getEmail().equals(email)) {
                p = true;
                break;
            }
        }
        return p;
    }

    public void consultaUsuario(String email) {
        Usuario user = null;
        for (Usuario usuario : usuarios) {
            if(usuario.getEmail().equals(email)) {
                user = usuario;
                break;
            }
        }
        
        if(user != null) {
            System.out.println("Nome: " + user.getNome());
            System.out.println("Email: " + user.getEmail());
            
            Alocacao al = null;
            int a1 = 0, a2 = 0;
            for (Alocacao alocacao: alocacoes) {
                if(alocacao.getUser().getEmail().equals(email)) {
                    al = alocacao;
                    System.out.println("Recurso alocado: (" + al.getRec().getId() + ") " + al.getRec().getTipo());
                    a1++;
                }
                
                if(participou(alocacao.getAtiv(), email)) {
                    al = alocacao;
                    System.out.println("Atividade realizada: " + al.getAtiv().getTitulo());
                    a2++;
                }
            }
            
            if(a1 == 0) {
                System.out.println("Nenhum recurso alocado!");
            }
            
            if(a2 == 0) {
                System.out.println("Nenhuma atividade realizada!");
            }
            
        } else {
            System.out.println("Usuário não encontrado!");
        }
    }

    public void consultaRecurso(int id) {
        Recurso rec = null;
        int aux = 0;
        
        for (Recurso recurso : recursos) {
            if(recurso.getId() == id) {
                rec = recurso;
                break;
            }
        }
        
        if(rec != null) {
            System.out.println("Id: " + rec.getId() );
            System.out.println("Tipo: " + rec.getTipo());
            System.out.println("Status: " + rec.getStatus());

            if(!rec.getStatus().equals("Disponível")) {
                System.out.println("Responsável:" + rec.getResponsavel().getNome());
                System.out.println(rec);
            } 
            
            if(rec.getAtividades().size() != 0) {
                for (Atividade ativ : rec.getAtividades()) {
                    System.out.println("Atividade: " + ativ.getTitulo());
                    for (Usuario usuario : ativ.getParticipantes()) {
                        System.out.println("Participante: " + usuario.getNome());
                    }
                }
            }
                        
        } else {
            System.out.println("Recurso não encontrado!");
        }
    }
}
