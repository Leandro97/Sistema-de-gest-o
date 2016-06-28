package negocio;

import com.sun.beans.util.Cache;
import java.util.Calendar;

/**
 * @author Leandro Martins
 */
public class Recurso {
    private int id;
    private String tipo;
    private Calendar dataInicio, dataFim;
    private Usuario responsavel;
    private String status;
    
    public Recurso(int id, String tipo, Usuario responsavel, String status) {
        this.id = id;
        this.tipo = tipo;
        this.responsavel = responsavel;
        this.status = status;
    }
    
    public Recurso(int id, String tipo, Usuario responsavel, String status, Calendar dataInicio, Calendar dataFim, Atividade atividade) {
        this.id = id;
        this.tipo = tipo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.responsavel = responsavel;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Calendar getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Calendar dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Calendar getDataFim() {
        return dataFim;
    }

    public void setDataFim(Calendar dataFim) {
        this.dataFim = dataFim;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }  
    
    public String toString() {
        return "Id: " + Integer.toString(id) + ". Tipo: " +  tipo + ". Responsável: " + responsavel.getNome() + ". Status: " + status + 
                "\nData e hora de início: " + getDataInicio().get(Calendar.DATE) + "/" + (getDataInicio().get(Calendar.MONTH) + 1)+ "/" + getDataInicio().get(Calendar.YEAR) + ". " + getDataInicio().get(Calendar.HOUR_OF_DAY)  + ":" + getDataInicio().get(Calendar.MINUTE)  + 
                "\nData e hora de fim: " + getDataFim().get(Calendar.DATE) + "/" + (getDataFim().get(Calendar.MONTH) + 1)+ "/" + getDataFim().get(Calendar.YEAR) + ". " + getDataFim().get(Calendar.HOUR_OF_DAY)  + ":" + getDataFim().get(Calendar.MINUTE) ;
    }
}
