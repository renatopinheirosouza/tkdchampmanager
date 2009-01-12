package aplicacao.lutador;

import aplicacao.interfaces.BuscaItem;
import controle.observerhub.LutadorObserverHub;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.table.AbstractTableModel;
import modelo.Campeonato;
import modelo.Lutador;
import modelo.excecoes.DAOException;
import modelo.excecoes.EquipeDadoIncorretoException;
import modelo.excecoes.LutadorDadoIncorretoException;

/**
 *
 * @author Renato Pinheiro
 */
public class TableModelLutador extends AbstractTableModel implements Observer, 
        BuscaItem {
    
    /** Dados do model */
    private List<Lutador> lutadores = null;
    
    /* Header das colunas */
    private String [] colunas = { "Código", "Nome", "Graduação", "Equipe"};
    
    /* Campeonato ao qual este model está ligado, usado na 
     gerência dos campeonatos*/
    private Campeonato campeonatoAtivo = null;
    
    /* Nome do metodo que deve ser chamado para repopular a colecao de lutadores
     quando o observador acionar este model*/
    private String nomeMetodo = "";
    
    public TableModelLutador() throws
            DAOException, LutadorDadoIncorretoException, 
            EquipeDadoIncorretoException {      
        
        this.setlutadores(Lutador.getLutadores());
        LutadorObserverHub.getInstance().addObserver(this);
    }    

    public TableModelLutador(List lista) {      
        
        this.setlutadores(lista);
        
    }    
    
    public TableModelLutador(Campeonato campeonato, String nomeMetodo) {      

        this.campeonatoAtivo = campeonato;
        this.nomeMetodo = nomeMetodo;
        
        this.atualizaViaMetodoGenerico();
        
    }        
    
    @Override
    public String getColumnName(int column) {
        
        return getColunas()[column];
        
    }
    
    /**
    * Retorna o numero de lutadores existentes no modelo
    * @see javax.swing.table.TableModel#getRowCount()
    */    
    @Override
    public int getRowCount() {
        return lutadores != null ? getlutadores().size() : 0;
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
    
    public List getlutadores() {
        return lutadores;
    }
    
    public void setColunas(String[] strings) {
        colunas = strings;
    }
    
    public void setlutadores(List lutadores) {
        this.lutadores = lutadores;
        this.fireTableDataChanged();
    }

    /**
    * Obtem o valor na linha e coluna
    * @see javax.swing.table.TableModel#getValueAt(int, int)
    */    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {        

        if(lutadores.get(rowIndex)==null)
            return null;
        
        if(getRowCount()>0){
            
            switch(columnIndex){
                case 0:
                    return lutadores.get(rowIndex).getId();
                case 1:
                    return lutadores.get(rowIndex).getNome();
                case 2:
                    
                    return Math.abs(lutadores.get(rowIndex).getGraduacao())+"º " 
                            + (lutadores.get(rowIndex).getGraduacao() > 0
                            ? "Gub" : "Dan");
                case 3:
                    
                    if(lutadores.get(rowIndex).getEquipe() == null) {
                        return "";
                    } else {
                        return lutadores.get(rowIndex).getEquipe().getNome();
                    }
                    
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
     *  Este metodo nos auxilia na seleção de um determinado lutador para
     * podermos manipular seus lutadores.
     */
    public Lutador getLutador(int index){
        
        // Para impedir que estoure os limites da lista
        if (index >= 0 && index <= lutadores.size()-1)
            return lutadores.get(index);

        return null;
    }    

    @Override
    public void update(Observable o, Object arg) {

        if(campeonatoAtivo == null){

            try {
                this.setlutadores(Lutador.getLutadores());
                this.fireTableDataChanged();
            } catch (DAOException ex) {
                util.Util.gravaExcecao(ex, this);
                throw new RuntimeException("Erro no acesso ao banco de dados");
            } catch (LutadorDadoIncorretoException ex) {
                util.Util.gravaExcecao(ex, this);
                throw new RuntimeException();
            } catch (EquipeDadoIncorretoException ex) {
                util.Util.gravaExcecao(ex, this);
                throw new RuntimeException();
            }
            
        } else {
            this.atualizaViaMetodoGenerico();    
        }
            
    }

    @Override
    public Object getUknowTypeItem(int index) {

        if (index >= 0 && index < lutadores.size())
            return this.getLutador(index);
        
        return null;    
        
    }
    
    @Override
    public int getRowOfObject(Object obj){
        
        Lutador lutador = (Lutador) obj;
        
        return lutadores.lastIndexOf(lutador);
        
    }
    
    private void atualizaViaMetodoGenerico(){
        
        // Colecao de tipos de Parâmetros para o getMethod
        Class partypes[] = new Class[0];
        
        // Colecao de argumento para o invoke
        Object arglist[] = new Object[0];
        
        try {
            Method metodoAChamar = campeonatoAtivo.getClass().getMethod(nomeMetodo, partypes);
            
            this.setlutadores((List) metodoAChamar.invoke(campeonatoAtivo,arglist));
            
        } catch (SecurityException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new RuntimeException();
        } catch (NoSuchMethodException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new RuntimeException();
        } catch (IllegalAccessException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new RuntimeException();
        } catch (InvocationTargetException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new RuntimeException();
        }
        
        this.fireTableDataChanged();
        
    }
        
}
