package modelo.dao.interfaces;

import java.util.ArrayList;
import modelo.Campeonato;
import modelo.Luta;
import modelo.excecoes.DAOException;

/**
 *
 * @author Renato Pinheiro
 */
public interface LutaDAO {

    public int save(Luta luta) throws DAOException ;
    
    public void delete(Luta luta) throws DAOException;

    public void update(Luta luta) throws DAOException;
    
    public void atualizaVencedor(Luta lutaEncerrada) throws DAOException;  
    
    public ArrayList lutasPorCampeonato(Campeonato campeonato) throws DAOException;    
    
    public void deleteLutaPorCampeonato(Campeonato campeonato);
        
}
