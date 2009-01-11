package modelo.dao.interfaces;

import modelo.Parametros;
import modelo.excecoes.DAOException;

/**
 *
 * @author Samir Azzam
 */
public interface ParametrosDAO {
    
    public void save(Parametros parametros) throws DAOException;
    
    public Parametros load() throws DAOException;
    
}