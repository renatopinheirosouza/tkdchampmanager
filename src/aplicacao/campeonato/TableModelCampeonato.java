package aplicacao.campeonato;

import aplicacao.interfaces.BuscaItem;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.table.AbstractTableModel;
import modelo.Campeonato;
import modelo.excecoes.DAOException;

/**
 *
 * @author Renato Pinheiro
 */
public class TableModelCampeonato extends AbstractTableModel
        implements Observer, BuscaItem {
    
    private ArrayList<Campeonato> campeonatos = null;
    private String [] colunas = { "Nome", "Início", "Fim", "Inscritos"};
    
    public TableModelCampeonato() throws DAOException {      
        
        this.setCampeonatos(Campeonato.listCampeonatos());
        Campeonato.addCampeonatoObserver(this);
        
    }    

    public TableModelCampeonato(ArrayList campeonatos) {      
        
        this.setCampeonatos(campeonatos);
        this.fireTableDataChanged();
        
    }    
    
    public String getColumnName(int column) {
        
        return getColunas()[column];
        
    }
    
    /**
    * Retorna o numero de linhas existentes no modelo
    * @see javax.swing.table.TableModel#getRowCount()
    */    
    public int getRowCount() {
        return campeonatos != null ? getCampeonatos().size() : 0;
    }

    /**
    * Retorna o numero de colunas no modelo
    * @see javax.swing.table.TableModel#getColumnCount()
    */    
    public int getColumnCount() {
        return getColunas().length;
    }
    
    public String[] getColunas() {
        return colunas;
    }
    
    public ArrayList getCampeonatos() {
        return campeonatos;
    }
    
    public void setColunas(String[] strings) {
        colunas = strings;
    }
    
    public void setCampeonatos(ArrayList campeonatos) {
        this.campeonatos = campeonatos;
        this.fireTableDataChanged();
    }

    /**
    * Obtem o valor na linha e coluna
    * @see javax.swing.table.TableModel#getValueAt(int, int)
    */    
    public Object getValueAt(int rowIndex, int columnIndex) {        
    
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        if(getRowCount()>0){
            
            switch(columnIndex){
                case 0:
                    return campeonatos.get(rowIndex).getNome();
                case 1:
                    return df.format(
                            campeonatos.get(rowIndex).getDataInicial().getTime());

                case 2:
                    return df.format(
                            campeonatos.get(rowIndex).getDataFinal().getTime());
                    
                case 3:
                    return campeonatos.get(rowIndex).getLutadoresInscritos().size();

                default:
                    return null;
            }
            
        }
        
        return null;
        
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    /**
     *  Este metodo nos auxilia na seleção de uma determinada equipe para
     * podermos manipular seus dados.
     */
    public Campeonato getCampeonato(int index){
        
        // Para impedir que estoure os limites da lista
        if (index >= 0 && index <= campeonatos.size()-1)
            return campeonatos.get(index);

        return null;
    }    

    public void update(Observable o, Object arg) {

        try {
            this.setCampeonatos(Campeonato.listCampeonatos());
        } catch (DAOException ex) {
            util.Util.gravaExcecao(ex, this);
        }
        
    }

    @Override
    public Object getUknowTypeItem(int index) {
        
        if (index >= 0 && index < campeonatos.size())
            return this.getCampeonato(index);
        
        return null;
        
    }
    
    @Override
    public int getRowOfObject(Object obj){
        
        Campeonato campeonato = (Campeonato) obj;
        
        return campeonatos.lastIndexOf(campeonato);
        
    }    
        
}
