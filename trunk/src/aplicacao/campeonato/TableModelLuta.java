package aplicacao.campeonato;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;
import javax.swing.table.AbstractTableModel;
import modelo.Campeonato;
import modelo.Luta;
import modelo.excecoes.DAOException;

/**
 *
 * @author Renato Pinheiro
 */
public class TableModelLuta extends AbstractTableModel
        implements Observer{
    
    private ArrayList<Luta> lutas = null;
    private String [] colunas = { "N.", "Protetor Vermelho", "Protetor " +
            "Azul",  "Categoria", "Status", "Rodada", "Vencedor"};
    
    private Campeonato campeonato = null;
    
    public TableModelLuta(Campeonato campeonato) throws DAOException {      
        
        this.setLutas(Luta.lutasPorCampeonato(campeonato));       
        this.campeonato = campeonato;
        Luta.addObserver(this);
        
    }    

    @Override
    public String getColumnName(int column) {
        
        return getColunas()[column];
        
    }
    
    /**
    * Retorna o numero de linhas existentes no modelo
    * @see javax.swing.table.TableModel#getRowCount()
    */    
    @Override
    public int getRowCount() {
        return lutas != null ? getLutas().size() : 0;
    }

    /**
    * Retorna o numero de colunas no modelo
    * @see javax.swing.table.TableModel#getColumnCount()
    */    
    @Override
    public int getColumnCount() {
        return getColunas().length;
    }
    
    public String[] getColunas() {
        return colunas;
    }
    
    public ArrayList getLutas() {
        return lutas;
    }
    
    public void setColunas(String[] strings) {
        colunas = strings;
    }
    
    /** Atualiza a coleção de lutas deste model */
    public void setLutas(ArrayList lutas) {

        this.lutas = lutas;
        
        // Ordenando as lutas pelo id da luta no campeonato
        Collections.sort( this.lutas, new CompararLutas());        
                
        this.fireTableDataChanged();
    }
    
    
    /**
    * Obtem o valor na linha e coluna
    * @see javax.swing.table.TableModel#getValueAt(int, int)
    */    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {        
    
        if(getRowCount()>0){
            
            switch(columnIndex){
                case 0:
                    return lutas.get(rowIndex).getIdLutaCampeonato();
                case 1:
                    // WO 
                    if(lutas.get(rowIndex).isWoLutador1())
                        return "===== WO =====";
                    
                    // Lutador
                    if(lutas.get(rowIndex).getLutador1() != null)
                        return lutas.get(rowIndex).getLutador1().getNome();                    
                    
                    // Buscando na luta anterior 1
                    if(lutas.get(rowIndex).getLutaAnterior1() !=null){

                        // Duplo WO
                        if(lutas.get(rowIndex).getLutaAnterior1().isWoLutador1() && lutas.get(rowIndex).getLutaAnterior1().isWoLutador2())
                            return "===== WO =====";
                        
                        // Vencedor
                        if(lutas.get(rowIndex).getLutaAnterior1().getLutadorVencedor() != null)
                            return lutas.get(rowIndex).getLutaAnterior1().getLutadorVencedor().getNome();
                        
                        // Aguardando
                        if(lutas.get(rowIndex).getLutaAnterior1() != null)                        
                            return "Aguardando luta N. " + lutas.get(rowIndex).getLutaAnterior1().getIdLutaCampeonato();
                        
                    }                        
                    
                    return "erro";
                case 2:
                    // WO 
                    if(lutas.get(rowIndex).isWoLutador2())
                        return "===== WO =====";
                    
                    // Lutador
                    if(lutas.get(rowIndex).getLutador2() != null)
                        return lutas.get(rowIndex).getLutador2().getNome();                    
                    
                    // Buscando na luta anterior 1
                    if(lutas.get(rowIndex).getLutaAnterior2() !=null){

                        // Duplo WO
                        if(lutas.get(rowIndex).getLutaAnterior2().isWoLutador1() && lutas.get(rowIndex).getLutaAnterior2().isWoLutador2())
                            return "===== WO =====";
                        
                        // Vencedor
                        if(lutas.get(rowIndex).getLutaAnterior2().getLutadorVencedor() != null)
                            return lutas.get(rowIndex).getLutaAnterior2().getLutadorVencedor().getNome();
                        
                        // Aguardando
                        if(lutas.get(rowIndex).getLutaAnterior2() != null)                        
                            return "Aguardando luta N. " + lutas.get(rowIndex).getLutaAnterior2().getIdLutaCampeonato();
                        
                    }                 
                    
                    return "erro";
                    
                case 3:
                    return lutas.get(rowIndex).getCategoria().getNome();
                    
                case 4:
                    if(lutas.get(rowIndex).getLutadorVencedor() != null &&
                            lutas.get(rowIndex).isPrimeiroLugar())
                        return "Campeão";
                    
                    if(lutas.get(rowIndex).isAtiva())
                        return "ATIVA";

                    if(lutas.get(rowIndex).isEncerrada())
                        return "ENCERRADA";
                    
                    if(lutas.get(rowIndex).getLutador1() != null &&
                            lutas.get(rowIndex).getLutador2() != null)
                        return "DISPONÍVEL";
                    else
                        return "INCOMPLETA";

                case 5:
                    return lutas.get(rowIndex).getRodada();
                    
                case 6:
                    if(lutas.get(rowIndex).getLutadorVencedor() != null)
                        return 
                            lutas.get(rowIndex).getLutadorVencedor().getNome();
                    else
                        return "";
                    
                default:
                    return null;
            }
            
        }
        
        return null;
        
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    /**
     *  Este metodo nos auxilia na seleção de uma determinada equipe para
     * podermos manipular seus dados.
     */
    public Luta getLuta(int index){
        
        // Para impedir que estoure os limites da lista
        if (index >= 0 && index <= lutas.size()-1)
            return lutas.get(index);

        return null;
    }    

    @Override
    public synchronized void update(Observable o, Object arg) {
        
        try {
            this.setLutas(Luta.lutasPorCampeonato(campeonato));
        } catch (DAOException ex) {
        }
        
    }

   /* Usado para fazer o sort da lutas pelo Id no campeonato */
    class CompararLutas implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            Luta luta1 = (Luta) o1;
            Luta luta2 = (Luta) o2;
            
            if(luta1.getIdLutaCampeonato() < luta2.getIdLutaCampeonato())
                return -1;
            
            if(luta1.getIdLutaCampeonato() > luta2.getIdLutaCampeonato())
                return 1;            
                
            return  0;
            
        }
    }
     
}
