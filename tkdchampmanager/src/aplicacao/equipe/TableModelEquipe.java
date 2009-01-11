package aplicacao.equipe;

import aplicacao.interfaces.BuscaItem;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.table.AbstractTableModel;
import modelo.Equipe;
import modelo.excecoes.DAOException;
import modelo.excecoes.EquipeDadoIncorretoException;

/**
 *
 * @author Renato Pinheiro
 */
public class TableModelEquipe extends AbstractTableModel implements Observer, 
        BuscaItem {
    
    private ArrayList<Equipe> equipes = null;
    private String [] colunas = { "Equipe", "Contato"};
    
    public TableModelEquipe() throws EquipeDadoIncorretoException,
            DAOException {      
        
        this.setEquipes(Equipe.listEquipes());
        Equipe.addEquipeObserver(this);
        
    }    

    public TableModelEquipe(ArrayList lista) {      
        
        this.setEquipes(lista);
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
        return equipes != null ? getEquipes().size() : 0;
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
    
    public ArrayList getEquipes() {
        return equipes;
    }
    
    public void setColunas(String[] strings) {
        colunas = strings;
    }
    
    public void setEquipes(ArrayList list) {
        equipes = list;
        this.fireTableDataChanged();
    }

    /**
    * Obtem o valor na linha e coluna
    * @see javax.swing.table.TableModel#getValueAt(int, int)
    */    
    public Object getValueAt(int rowIndex, int columnIndex) {        
    
        if(getRowCount()>0){
            
            switch(columnIndex){
                case 0:
                    return equipes.get(rowIndex).getNome();
                case 1:
                    return equipes.get(rowIndex).getNomeContato();
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
    public Equipe getEquipe(int index){
        
        // Para impedir que estoure os limites da lista
        if (index >= 0 && index <= equipes.size()-1)
            return equipes.get(index);

        return null;
    }    

    @Override
    public void update(Observable o, Object arg) {

        try {
            this.setEquipes(Equipe.listEquipes());
            this.fireTableDataChanged();
        } catch (DAOException ex) {
            util.Util.gravaExcecao(ex, this);
        }

        
    }

    public Object getUknowTypeItem(int index) {

        if (index >= 0 && index < equipes.size())
            return this.getEquipe(index);
        
        return null;                
        
    }

    public int getRowOfObject(Object obj){
        
        Equipe equipe = (Equipe) obj;
        
        return equipes.lastIndexOf(equipe);
        
    }      
        
}
