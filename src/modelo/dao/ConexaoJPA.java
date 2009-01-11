package modelo.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 */
public class ConexaoJPA {
    
    private static ConexaoJPA conexaoJPA;
    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    
    /** Creates a new instance of ConexaoDB */
    private ConexaoJPA() {}
    
    public static ConexaoJPA getInstance(){
        
        if (conexaoJPA == null) {
            conexaoJPA = new ConexaoJPA();
        }
        
        return conexaoJPA;
    }
        
    public EntityManager getEntityManager(){
        
        if(em == null){
            emf = Persistence.createEntityManagerFactory("tkdchampmanager");
            em = emf.createEntityManager();
        }
        
        return em;
        
    }
    
    public void closeConnection() {
        
        em.close();
        emf.close();
        
    }
    
}
