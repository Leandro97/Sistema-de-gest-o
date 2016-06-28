package negocio;

/**
 * @author Leandro Martins
 */
public class Alocacao {
    private Usuario user;
    private Recurso rec;
    private Atividade ativ;
    
    public Alocacao() {
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Recurso getRec() {
        return rec;
    }

    public void setRec(Recurso rec) {
        this.rec = rec;
    }

    public Atividade getAtiv() {
        return ativ;
    }

    public void setAtiv(Atividade ativ) {
        this.ativ = ativ;
    }
    
    
}
