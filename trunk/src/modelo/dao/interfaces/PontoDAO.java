package modelo.dao.interfaces;

import java.util.ArrayList;
import modelo.Luta;
import modelo.Ponto;
import modelo.excecoes.DAOException;

/**
 *
 * @author Renato Pinheiro
 */
public interface PontoDAO {    
    
    public int save(Ponto ponto) throws DAOException;
    
    public void delete(Ponto ponto) throws DAOException;
    
    public ArrayList listPontoPorLuta(Luta luta) throws DAOException;

}
