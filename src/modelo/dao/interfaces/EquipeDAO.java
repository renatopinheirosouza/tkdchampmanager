package modelo.dao.interfaces;

import java.util.ArrayList;
import modelo.Equipe;
import modelo.excecoes.DAOException;

/**
 *
 * @author Renato Pinheiro
 */
public interface EquipeDAO {
    
    public int save(Equipe equipe) throws DAOException;

    public void delete(Equipe equipe) throws DAOException;

    public void update(Equipe equipe) throws DAOException;

    public ArrayList listEquipes() throws DAOException;
    
    public ArrayList listEquipesPorNome(String nome) throws DAOException;

    public Equipe getEquipePorId(int id) throws DAOException;
    
    public Equipe getEquipeporNome(String nome) throws DAOException;
        
}
