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
                    } else if(email.equals("admin")) {
                        menuAdmin();
                    } else if(loggedUser.getCargo().equals("Aluno")){
                        menuAluno();
                    } else{
                        menuComum();
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
    
    private static void menuAluno() {
        int opc;
        
        for(;;) {
            user = null;
            System.out.println("---");
            System.out.println("1 - Consultar recurso");
            System.out.println("2 - Consultar usuário");
            System.out.println("3 - Relatório");
            System.out.println("4 - Logout");
            opc = read.nextInt();

            switch(opc) {
                case 1: //consultar recurso
                    consultarRecurso();
                    break;
                case 2: //consultar usuário
                    consultarUsuario();
                    break;
                case 3: //relatório
                    relatorio();
                    break;
                case 4: //logout 
                    loggedUser = null;
                    break;    
            }
            
            if(opc == 4) break;
        }
    }
    
    private static void menuComum() {
        int opc;
        
        for(;;) {
            user = null;
            System.out.println("---");
            System.out.println("1 - Alocar recurso");
            System.out.println("2 - Confirmar alocação");
            System.out.println("3 - Consultar recurso");
            System.out.println("4 - Consultar usuário");
            System.out.println("5 - Relatório");
            System.out.println("6 - Logout");
            opc = read.nextInt();

            switch(opc) {
                case 1: //alocar recurso 
                    alocarRecurso();
                    break;
                case 2: //confirmar alocação
                    confirmarAlocacao();
                    break;
                case 3: //consultar recurso
                    consultarRecurso();
                    break;
                case 4: //consultar usuário
                    consultarUsuario();
                    break;
                case 5: //relatório
                    relatorio();
                    break;
                case 6: //logout 
                    loggedUser = null;
                    break;    
            }
            
            if(opc == 6) break;
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
            
                        
        if(loggedUser.getCargo().equals("Aluno")) {
            System.out.println("Permissão negada!");
        } else {
            banco.cadastrarRecurso(tipoS);
            System.out.println("Recurso cadastrado com sucesso!");
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
                            rec.setResponsavel(loggedUser);
                            banco.cadastraAlocacao(rec, loggedUser);
                            System.out.println("Alocação bem sucedida!");
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
                        if(!banco.getUsuarios().get(i).equals(rec.getResponsavel())) {
                            System.out.println("Id: " + i + ". Nome: " + banco.getUsuarios().get(i).getNome());
                        }
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

    public static void consultarUsuario() {
        String email;
        System.out.println("Digite o email do usuário a ser consultado.");
        email = read.nextLine();
        email = read.nextLine();
        
        banco.consultaUsuario(email);
        
        System.out.println("Digite algo para sair.");
        read.nextLine();
    }

    public static void consultarRecurso() {
        int id;
        System.out.println("Digite o id do recurso a ser consultado.");
        id = read.nextInt();
        
        banco.consultaRecurso(id);
        System.out.println("Digite algo para sair.");
        read.nextLine();
        read.nextLine();
    }

    private static void relatorio() {
        int i, n1 = 0, n2 = 0, n3 = 0, n4 = 0;
        System.out.println("Número de usuários cadastrados: " + banco.getUsuarios().size());
        
        for (Alocacao al : banco.getAlocacoes()) {
            if(al.getRec().getStatus().equals("Em processo de alocação")) {
                n1++;
            } else if(al.getRec().getStatus().equals("Alocado")) {
                n2++;
            } else if(al.getRec().getStatus().equals("Em andamento")) {
                n3++;
            } else if(al.getRec().getStatus().equals("Concluído")) {
                n4++;
            }
        }
        
        System.out.println("Recursos \"em processo de alocação\": " + n1);
        System.out.println("Recursos \"alocados\": " + n2);
        System.out.println("Recursos \"em andamento\": " + n3);
        System.out.println("Recursos \"concluídos\": " + n4);
        
        n1 = 0;
        n2 = 0;
        n3 = 0;
        
        System.out.println("Número total de alocações: " + banco.getAlocacoes().size());
        
        if(n4 != 0) {
            for (i = 0; i < n4; i++) {
                if(banco.getAlocacoes().get(i).getAtiv().getTipo().equals("Aula tradicional")) {
                    n1++;
                } else if(banco.getAlocacoes().get(i).getAtiv().getTipo().equals("Apresentações")) {
                    n2++;
                } else if(banco.getAlocacoes().get(i).getAtiv().getTipo().equals("Laboratório")) {
                    n3++;
                }
            }

            System.out.println("Atividades realizadas: ");
            System.out.println("Aulas tradicionais: " + n1);
            System.out.println("Apresentações: " + n2);
            System.out.println("Laboratórios: " + n3);
        } else {
            System.out.println("Nenhuma ativdade cadastrada!");
        }
        
        System.out.println("Digite qualquer coisa para sair.");
        read.nextLine();
        read.nextLine();
    }
}
