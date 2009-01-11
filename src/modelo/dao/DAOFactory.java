package modelo.dao;

import modelo.dao.interfaces.CampeonatoDAO;
import modelo.dao.interfaces.CategoriaDAO;
import modelo.dao.interfaces.EquipeDAO;
import modelo.dao.interfaces.LutaDAO;
import modelo.dao.interfaces.LutadorDao;
import modelo.dao.interfaces.ParametrosDAO;
import modelo.dao.interfaces.PontoDAO;

/**
 *
 * @author Renato Pinheiro
 */
public class DAOFactory {
    
    private DAOFactory() {
    }
    
    public static DAOFactory getInstance() {
        
        return new DAOFactory();
        
    }
    
    public LutadorDao getLutadorDAO() {
        
        return new JPALutadorDAO();
        
    }
    
    public ParametrosDAO getParametrosDAO() {
        
        return new SerializadoParametrosDAO();
        
    }


    public EquipeDAO getEquipeDAO() {
        
        return new JPAEquipeDAO();
        
    }

    public CategoriaDAO getCategoriaDAO() {
        
        return new JPACategoriaDAO();
        
    }    
    
    public CampeonatoDAO getCampeonatoDAO() {
        
        return new JPACampeonatoDAO();
        
    }     
    
    public LutaDAO getLutaDAO() {
        
        return new JPALutaDAO();
        
    }       
    
    public PontoDAO getPontoDAO(){
        
        return new JPAPontoDAO();
        
    }
    
}
