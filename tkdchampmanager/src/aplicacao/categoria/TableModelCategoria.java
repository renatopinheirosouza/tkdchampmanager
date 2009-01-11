package aplicacao.categoria;

import aplicacao.interfaces.BuscaItem;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.table.AbstractTableModel;
import modelo.Campeonato;
import modelo.Categoria;
import modelo.excecoes.CategoriaDadoIncorretoException;
import modelo.excecoes.DAOException;

/**
 *
 * @author Renato Pinheiro
 */
public class TableModelCategoria extends AbstractTableModel
        implements Observer, BuscaItem {
    
    /** Dados do model */
    private List<Categoria> categorias = null;
    
    /** Header das colunas */
    private String [] colunas = { "Nome", "Peso", "Idade", "Graduacao", "Sexo"};

    /* Campeonato ao qual este model está ligado, usado na 
     gerência dos campeonatos*/
    private Campeonato campeonatoAtivo = null;
    
    /* Nome do metodo que deve ser chamado para repopular a colecao de lutadores
     quando o observador acionar este model*/
    private String nomeMetodo = "";    
    
    public TableModelCategoria() throws
            CategoriaDadoIncorretoException, DAOException {      
        
        this.setCategorias(Categoria.listCategorias());
        Categoria.addCategoriaObserver(this);
        
    }    

    public TableModelCategoria(List lista) {      
        
        this.setCategorias(lista);
        
    }    
    
    public TableModelCategoria(Campeonato campeonato, String nomeMetodo) {      

        this.campeonatoAtivo = campeonato;
        this.nomeMetodo = nomeMetodo;
        
        this.atualizaViaMetodoGenerico();
        
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
        return categorias != null ? getCategorias().size() : 0;
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
    
    public List getCategorias() {
        return categorias;
    }
    
    public void setColunas(String[] strings) {
        colunas = strings;
    }
    
    public void setCategorias(List categorias) {
        this.categorias = categorias;
        this.fireTableDataChanged();
    }

    /**
    * Obtem o valor na linha e coluna
    * @see javax.swing.table.TableModel#getValueAt(int, int)
    */    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {        
    
        String dados = "";
        
        if(getRowCount()>0){
            
            switch(columnIndex){
                case 0:
                    return categorias.get(rowIndex).getNome();
                case 1:
                    // Peso
                    dados = "De "+categorias.get(rowIndex).getPesoMinimo() +
                            " a "+categorias.get(rowIndex).getPesoMaximo() +
                            " kilos";
                    
                    return dados;

                case 2:
                    // Idade
                    dados = "De "+categorias.get(rowIndex).getIdadeMinima() +
                            " a "+categorias.get(rowIndex).getIdadeMaxima() +
                            " anos";
                    
                    return dados;                    
                    
                case 3:
                    // Graduacao
                    dados = "De "+ 
                            Math.abs(categorias.get(rowIndex).getGraduacaoMinima())+
                            (categorias.get(rowIndex).getGraduacaoMinima() > 0
                            ? " Gub a " : " Dan a ") +
                            Math.abs(categorias.get(rowIndex).getGraduacaoMaxima())+
                            (categorias.get(rowIndex).getGraduacaoMaxima() > 0
                            ? " Gub" : " Dan");
                    
                    return dados;                    

                case 4:
                    return (
                        categorias.get(rowIndex).getSexo().equalsIgnoreCase("M") ?
                            "Masculino" : "Feminino");
                    
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
    public Categoria getCategoria(int index){
        
        // Para impedir que estoure os limites da lista
        if (index >= 0 && index <= categorias.size()-1)
            return categorias.get(index);

        return null;
    }    

    @Override
    public void update(Observable o, Object arg) {

        if(campeonatoAtivo==null){
            try {
                this.setCategorias(Categoria.listCategorias());
            } catch (CategoriaDadoIncorretoException ex) {
                util.Util.gravaExcecao(ex, this);
            } catch (DAOException ex) {
                util.Util.gravaExcecao(ex, this);
            }
        } else {
            this.atualizaViaMetodoGenerico();
        }
        
    }

    @Override
    public Object getUknowTypeItem(int index) {
        
        if (index >= 0 && index < categorias.size())
            return this.getCategoria(index);
        
        return null;
        
    }
    
    @Override
    public int getRowOfObject(Object obj){
        
        Categoria categoria = (Categoria) obj;
        
        return categorias.lastIndexOf(categoria);
        
    }    

    private void atualizaViaMetodoGenerico(){
        
        // Colecao de tipos de Parâmetros para o getMethod
        Class partypes[] = new Class[0];
        
        // Colecao de argumento para o invoke
        Object arglist[] = new Object[0];
        
        try {
            Method metodoAChamar = campeonatoAtivo.getClass().getMethod( 
                    nomeMetodo, partypes);
            
            this.setCategorias(
                    (List<Categoria>) metodoAChamar.invoke(campeonatoAtivo,
                    arglist));
            
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
