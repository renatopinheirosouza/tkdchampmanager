package modelo.dao.interfaces;

import java.util.ArrayList;
import modelo.Categoria;
import modelo.excecoes.DAOException;

/**
 *
 * @author Renato Pinheiro
 */
public interface CategoriaDAO {
    
    public int save(Categoria categoria) throws DAOException;
    
    public void delete(Categoria categoria) throws DAOException;

    public void update(Categoria categoria) throws DAOException;

    public ArrayList listCategorias() throws DAOException;
    
    public ArrayList listCategoriasPorNome(String nome) throws DAOException;
    
    public Categoria getCategoriaPorId(int id) throws DAOException;
    
    public Categoria getCategoriaPorNome(String nome) throws DAOException;
    
}
