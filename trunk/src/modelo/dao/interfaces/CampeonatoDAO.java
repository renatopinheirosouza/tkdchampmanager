package modelo.dao.interfaces;

import java.util.ArrayList;
import modelo.Campeonato;
import modelo.excecoes.DAOException;

/**
 *
 * @author Renato Pinheiro
 */
public interface CampeonatoDAO {
    
    public int save(Campeonato campeonato) throws DAOException;
    
    public void delete(Campeonato campeonato) throws DAOException;
    
    public void update(Campeonato campeonato) throws DAOException;
    
    public ArrayList listCampeonatos() throws DAOException;
    
    public ArrayList listCampeonatoPorNome(String nome) throws DAOException;

    public Campeonato getCampeonatoPorId(int id) throws DAOException;
    
    public Campeonato getCampeonatoPorNome(String nome) throws DAOException;
         
}
