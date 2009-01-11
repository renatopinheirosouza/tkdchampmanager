package aplicacao.lutador;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import modelo.Equipe;
import modelo.excecoes.DAOException;

/**
 *
 * @author Renato Pinheiro
 */
public class ComboBoxModelEquipe extends AbstractListModel 
        implements ComboBoxModel, Observer {
    
    private Object selectedItem;
  
    private ArrayList dados;

    public ComboBoxModelEquipe() throws DAOException {
        dados = Equipe.listEquipes();
        
        Equipe.addEquipeObserver(this);
        
    }

    @Override
    public Object getSelectedItem() {
        return selectedItem;
    }
    
    @Override
    public void setSelectedItem(Object newValue) {
        
        // Seta o item selecionado
        selectedItem = newValue;
        
        // Notifica aos utilizadores do modelo
        this.fireContentsChanged(this, -1, -1);
    }

    @Override
    public int getSize() {
        return dados.size();
    }

    @Override
    public Object getElementAt(int i) {
        return dados.get(i);
    }

    @Override
    public void update(Observable o, Object arg) {
        
        try {
            dados = Equipe.listEquipes();
        } catch (DAOException ex) {
            throw new RuntimeException("Erro no acesso ao banco de dados");
        }        
        
        // Notifica aos utilizadores do modelo
        this.fireContentsChanged(this, -1, -1);        
        
    }
  
}
