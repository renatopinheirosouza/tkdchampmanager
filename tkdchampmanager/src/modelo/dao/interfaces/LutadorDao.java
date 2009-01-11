package modelo.dao.interfaces;

import java.util.ArrayList;
import modelo.Lutador;
import modelo.excecoes.DAOException;

/**
 *
 * @author Renato Pinheiro
 */
public interface LutadorDao {
    
    public int save(Lutador lutador) throws DAOException;
    
    public void delete(Lutador lutador) throws DAOException;

    public void update(Lutador lutador) throws DAOException;

    public ArrayList listLutadores() throws DAOException;
    
    public ArrayList getLutadoresPorNome(String nome) throws DAOException;
    
    public Lutador getLutadorPorId(int id) throws DAOException;
    
    public Lutador getLutadorPorDocumento(String Documento) throws DAOException;
    
}
