/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import banco.Banco;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import negocio.Alocacao;
import negocio.Recurso;
import negocio.Usuario;
import negocio.Atividade;

/**
 *
 * @author Leandro Martins
 */
public class Main {
    static Banco banco = new Banco();
    static Scanner read = new Scanner(System.in);
    static Usuario user = null;
    static Usuario loggedUser = null;
    static Recurso rec;
    static Atividade ativ;
    
     public static void main(String[] args) {
        int opc, cargo;
        String email, senha, nome;
        String cargoS = null;
        
        user = new Usuario("admin", "admin", "admin", "Administrador");
        banco.cadastrarUsuario(user);
        
        for(;;) {
            System.out.println("---");
            System.out.println("Sistema de gerência de recursos");
            System.out.println("1 - Login");
            System.out.println("2 - Cadastrar usuário");
            System.out.println("3 - Sair");
            opc = read.nextInt();
            
            switch(opc) {
                case 1: //logar
                    System.out.println("---");
        
                    System.out.println("Email: ");
                    email = read.nextLine();
                    email = read.nextLine();
                    System.out.println("Senha: ");
                    senha = read.nextLine();

                    loggedUser = banco.autenticar(email, senha);
                    if(loggedUser == null) {
                        System.out.println("Email ou senha incorretos.");
                    } else if(email.equals("admin") || email.equals("a")) {
                        menuAdmin();
                    }
                    break;
                case 2: //cadastrar usuário
                    System.out.println("---");
                    System.out.println("Seu nome: ");
                    nome = read.nextLine();
                    nome = read.nextLine();
                    System.out.println("Email: ");
                    email = read.nextLine();
                    System.out.println("Senha: ");
                    senha = read.nextLine();
                    
                    System.out.println("1 - Aluno de graduação, mestrado ou doutorado");
                    System.out.println("2 - Professor");
                    System.out.println("3 - Pesquisador");
                    System.out.println("Informe seu cargo: ");
                    cargo = read.nextInt();
                    
                    switch(cargo) {
                        case 1: cargoS = "Aluno";
                                break;
                        case 2: cargoS = "Professor";
                                break;
                        case 3: cargoS = "Pesquisador";
                                break;
                    }
                    
                    user = new Usuario(nome, email, senha, cargoS);
                    banco.cadastrarUsuario(user);
                    System.out.println("Usuário cadastrado com sucesso!");

                    break;
                case 3: break; //sair      
            }
            
            if(opc == 3) break;
        }
     }                

    public static void menuAdmin() {
        int opc;
        
        for(;;) {
            user = null;
            System.out.println("---");
            System.out.println("1 - Cadastrar recurso");
            System.out.println("2 - Alocar recurso");
            System.out.println("3 - Iniciar alocação");
            System.out.println("4 - Confirmar alocação");
            System.out.println("5 - Concluir alocação");
            System.out.println("6 - Consultar recurso");
            System.out.println("7 - Consultar usuário");
            System.out.println("8 - Relatório");
            System.out.println("9 - Logout");
            opc = read.nextInt();

            switch(opc) {
                case 1: //cadastrar recurso
                    cadastrarRecurso();
                    break;
                case 2: //alocar recurso 
                    alocarRecurso();
                    break;
                case 3: //iniciar alocação
                    iniciarAlocacao();
                    break;
                case 4: //confirmar alocação
                    confirmarAlocacao();
                    break;
                case 5: //concluir alocação  
                    concluirAlocacao();
                    break;
                case 6: //consultar recurso
                    consultarRecurso();
                    break;
                case 7: //consultar usuário
                    consultarUsuario();
                    break;
                case 8: //relatório
                    relatorio();
                    break;
                case 9: //logout 
                    loggedUser = null;
                    break;    
            }
            
            if(opc == 9) break;
        }
    }

    public static void cadastrarRecurso() {
        int tipo;
        String tipoS = null;
        String nome;
        user = null;

        System.out.println("---");
        System.out.println("1 - Laboratório");
        System.out.println("2 - Sala de aula");
        System.out.println("3 - Auditório");
        System.out.println("4 - Projetor");
        System.out.println("Informe o tipo de recurso: ");
        tipo = read.nextInt();

        switch(tipo) {
            case 1: tipoS = "Laboratório";
                    break;
            case 2: tipoS = "Sala de aula";
                    break;
            case 3: tipoS = "Auditório";
                    break;
            case 4: tipoS = "Projetor";
                    break;
        }
                    
                    
        while(user == null) {
            System.out.println("Informe o nome do responsável pelo recurso");
            nome = read.nextLine();
            nome = read.nextLine();
                        
            for (Usuario usuario : banco.getUsuarios()) {
                if((usuario.getNome().equals(nome)) && (!usuario.getCargo().equals("Aluno"))) {
                    user = usuario;
                    break;
                } 
            }
                        
            if(user == null) {
                System.out.println("Usuário não encontrado ou permissão negada!");
            } else {
                banco.cadastrarRecurso(tipoS, user);
                System.out.println("Recurso cadastrado com sucesso!");
            }
        }
    }

    public static void alocarRecurso() {
        int id;
        rec = null;
        banco.printRecursos();
        System.out.println("---");
                    
            if(banco.getRecursosQnt() == 0) {
                System.out.println("Nenhum recurso cadastrado!");
            }
                    
            while(rec == null && banco.getRecursosQnt() != 0) {
                System.out.println("Informe o Id do recurso a ser alocado:");
                id = read.nextInt();
                        
                for (Recurso recurso : banco.getRecursos()) {
                    if(recurso.getId() == id && recurso.getStatus().equals("Disponível")) {
                        rec = recurso;
                    } 
                }
                        
                if(rec == null) {
                    System.out.println("Recurso não encontrado ou em utilização!");
                     break;
                } else {
                    DateFormat d = new SimpleDateFormat ("dd/MM/yyyy");
                    SimpleDateFormat h = new SimpleDateFormat("HH:mm");
                    d.setLenient(false); 
                    h.setLenient(false); 
                            
                    String array[] = new String[3];
                    String dataS;
                            
                    Calendar c = Calendar.getInstance();
                                                       
                    System.out.println("Informe a data(dd/MM/yyyy) de início da alocação: ");
                    dataS = read.nextLine();
                    dataS = read.nextLine();
                            
                    try {
                        d.parse(dataS);
                    } catch (ParseException ex) {
                        System.out.println("Data inválida! Alocação cancelada.");
                         break;
                    }
                            
                        array = dataS.split("/");
                        c.set(Calendar.DATE, Integer.parseInt(array[0]));
                        c.set(Calendar.MONTH, Integer.parseInt(array[1]) - 1);
                        c.set(Calendar.YEAR, Integer.parseInt(array[2]));
                            
                        System.out.println("Informe a hora(hh:MM) de início da alocação: ");
                        dataS = read.nextLine();
                        dataS = dataS.concat(":00");
                        array = dataS.split(":");
                            
                        try {
                            h.parse(dataS);
                        } catch (ParseException ex) {
                            System.out.println("Horário inválido! Alocação cancelada.");
                            break;
                        }
                            
                        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(array[0]));
                        c.set(Calendar.MINUTE, Integer.parseInt(array[1]));
                        c.set(Calendar.SECOND, Integer.parseInt(array[2]));

                        rec.setDataInicio(c);
                            
                        c = Calendar.getInstance();
                            
                        System.out.println("Informe a data(dd/MM/yyyy) de fim da alocação: ");
                        dataS = read.nextLine();
                            
                        try {
                            d.parse(dataS);
                        } catch (ParseException ex) {
                            System.out.println("Data inválida! Alocação cancelada.");
                            break;
                        }
                            
                        array = dataS.split("/");
                        c.set(Calendar.DATE, Integer.parseInt(array[0]));
                        c.set(Calendar.MONTH, Integer.parseInt(array[1]) - 1);
                        c.set(Calendar.YEAR, Integer.parseInt(array[2]));
                            
                        System.out.println("Informe a hora(hh:MM) de fim da alocação: ");
                        dataS = read.nextLine();
                        dataS = dataS.concat(":00");
                        array = dataS.split(":");
                        
                        try {
                            h.parse(dataS);
                        } catch (ParseException ex) {
                            System.out.println("Horário inválido! Alocação cancelada.");
                            break;
                        }
                           
                        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(array[0]));
                        c.set(Calendar.MINUTE, Integer.parseInt(array[1]));
                        c.set(Calendar.SECOND, Integer.parseInt(array[2]));
                        
                        rec.setDataFim(c);
                        
                        if(rec.getDataInicio().after(rec.getDataFim())) {
                            System.out.println("Datas inválidas. A alocação será cancelada.");
                            rec.setDataInicio(null);
                            rec.setDataFim(null);
                            break;
                        } else {
                            rec.setStatus("Em processo de alocação");
                            System.out.println("Alocação bem sucedida!");
                            banco.cadastraAlocacao(rec, loggedUser);
                        }
                        //System.out.println(rec);
                }
            }
    }

    public static void iniciarAlocacao() {
        List<Alocacao> alocacoes = banco.getAlocacoes();
        List<Recurso> recursos = new ArrayList<Recurso>();
        int id;
        
        Recurso rec = null;
        
        System.out.println("---\nRecursos: ");
        for (Alocacao al : alocacoes) {
            if(al.getRec().getStatus().equals("Em processo de alocação")) {
                recursos.add(al.getRec());
                System.out.println("Id: " + al.getRec().getId() + ". Tipo: " + al.getRec().getTipo() + ". Status: Em processo de alocação.");
            }
        }
        
        if(recursos.size() == 0) {
            System.out.println("Nenhum recurso para iniciar.");
            return;
        }
        
        while(rec == null) {
            System.out.println("Digite o id do recurso que será iniciado.");
            id = read.nextInt();
            
            for (Recurso recurso : recursos) {
                if(recurso.getId() == id) {
                    rec = recurso;
                    break;
                } else {
                    System.out.println("Recurso não encontrado!");
                }
            }
            
            if(rec != null) {
                banco.iniciarAlocacao(id);
                System.out.println("Alocação iniciada!");    
                break;
            }
        }
    }

    public static void confirmarAlocacao() {
        List<Alocacao> alocacoes = banco.getAlocacoes();
        List<Recurso> recursos = new ArrayList<Recurso>();
        int id;
        
        Recurso rec = null;
        
        System.out.println("---\nRecursos: ");
        for (Alocacao al : alocacoes) {
            if((al.getRec().getResponsavel().getEmail().equals(loggedUser.getEmail())) && (al.getRec().getStatus().equals("Alocado"))) {
                if(al.getRec().getResponsavel().getLivre() == 0) {
                    System.out.println("Usuário associado a uma alocação em andamento!");
                    break;
                }
                
                recursos.add(al.getRec());
                System.out.println("Id: " + al.getRec().getId() + ". Tipo: " + al.getRec().getTipo() + ". Status: Alocado.");
            }
        }
        
        if(recursos.size() == 0) {
            System.out.println("Nenhum recurso para confirmar.");
            return;
        }
        
        while(rec == null) {
            System.out.println("Digite o id do recurso cuja alocação será confirmada.");
            id = read.nextInt();
            
            for (Recurso recurso : recursos) {
                if(recurso.getId() == id) {
                    rec = recurso;
                    break;
                } else {
                    System.out.println("Recurso não encontrado!");
                }
            }
            
            if(rec != null) {
                banco.confirmarAlocacao(id);
                System.out.println("Alocação confirmada!");    
                break;
            }
        }    
    }

    public static void concluirAlocacao() {
        List<Alocacao> alocacoes = banco.getAlocacoes();
        List<Recurso> recursos = new ArrayList<Recurso>();
        int id;
        
        Recurso rec = null;
        
        System.out.println("---\nRecursos: ");
        for (Alocacao al : alocacoes) {
            if(al.getRec().getStatus().equals("Em andamento")) {            
                recursos.add(al.getRec());
                System.out.println("Id: " + al.getRec().getId() + ". Tipo: " + al.getRec().getTipo() + ". Status: Em andamento.");
            }
        }
        
        if(recursos.size() == 0) {
            System.out.println("Nenhum recurso a ser concluído.");
            return;
        }
        
        while(rec == null) {
            System.out.println("Digite o id do recurso cuja alocação será concluída.");
            id = read.nextInt();
            
            for (Recurso recurso : recursos) {
                if(recurso.getId() == id) {
                    rec = recurso;
                    break;
                } else {
                    System.out.println("Recurso não encontrado!");
                }
            }
            
            if(rec != null) {
                int aux, opc, i;
                String tipoAS = null, titulo, descricao, material;
                List<Usuario> participantes = new ArrayList<Usuario>();
                Atividade at;
                
                if(rec.getResponsavel().getCargo().equals("Professor")) {
                    System.out.println("Escolha o tipo de atividade realizada: ");
                    System.out.println("1 - Aula tradicional");
                    System.out.println("2 - Apresentações");
                    System.out.println("3 - Laboratório");
                    aux = read.nextInt();
                
                    switch(aux) {
                        case 1: tipoAS = "Aula tradicional";
                                break;
                        case 2: tipoAS = "Apresentações";
                                break;
                        case 3: tipoAS = "Laboratório";
                                break;
                    }
                } else {
                    tipoAS = "Apresentações";
                }
                        
                System.out.println("Informe o título da atividade: ");
                titulo = read.nextLine();
                titulo = read.nextLine();
                
                System.out.println("Informe a descrição da atividade: ");
                descricao = read.nextLine();
                
                System.out.println("Informe o material utilizado na atividade: ");
                material = read.nextLine();
                
                System.out.println("Digite o id dos participantes da atividade(um por um) ou -1 para sair");
                do {
                    for (i = 0; i < banco.getUsuarios().size(); i++) {
                        System.out.println("Id: " + i + ". Nome: " + banco.getUsuarios().get(i).getNome());
                    }
                    opc = read.nextInt();
                    
                    if((opc < -1) || (opc > banco.getUsuarios().size())) {
                        System.out.println("Opção inexistente!");
                    } else if (opc != -1) {
                        participantes.add(banco.getUsuarios().get(opc));
                    }

                } while(opc != -1);
                at = new Atividade(tipoAS, titulo, descricao, participantes, material);
                banco.concluirAlocacao(rec.getId(), at);
                System.out.println("Alocação concluída!"); 
                
                List<Alocacao> als = banco.getAlocacoes();
            }
                break;
            }
        }        

    public static void consultarRecurso() {
       
    }

    public static void consultarUsuario() {
        
    }

    private static void relatorio() {
        
    }
}
