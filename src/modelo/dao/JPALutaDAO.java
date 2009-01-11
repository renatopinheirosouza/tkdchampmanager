package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import modelo.Campeonato;
import modelo.Luta;
import modelo.dao.interfaces.LutaDAO;
import modelo.excecoes.DAOException;

/**
 *
 * @author Renato Pinheiro
 */
public class JPALutaDAO implements LutaDAO{

    private EntityManager em = null;
    
    public JPALutaDAO() {

        em = ConexaoJPA.getInstance().getEntityManager();
      
    }
    
    @Override
    public int save(Luta luta) throws DAOException {
        
        try {
            
            em.getTransaction().begin();

            em.persist(luta);
            
            em.getTransaction().commit();
            
        } catch (EntityExistsException ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Luta já cadastrada", ex.getCause());
        } catch (IllegalStateException ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (TransactionRequiredException  ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());            
        }
        
        // Ajustar interface após testes com JPA (TODO)
        return luta.getId();
        
    }
    
    @Override
    public void delete(Luta luta) throws DAOException {
        
        try {
            
            em.getTransaction().begin();

            em.remove(luta);
            
            em.getTransaction().commit();
            
        } catch (IllegalStateException ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (TransactionRequiredException  ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());            
        } 
                
    }

    @Override
    public void update(Luta luta) throws DAOException {

        try {
            
            em.getTransaction().begin();

            em.merge(luta);
            
            em.getTransaction().commit();
            
        } catch (IllegalStateException ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (TransactionRequiredException  ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());            
        }
        
    }

    @Override
    public void atualizaVencedor(Luta lutaEncerrada) throws DAOException {

        this.update(lutaEncerrada);
        
    }
    
    /**
     * Lista as lutas deste campeonato
     */
    @Override
    public ArrayList lutasPorCampeonato(Campeonato campeonato) throws DAOException {
        
        List<Luta> lutas = null;
        
        try {
            
            Query q = em.createQuery("select luta from Luta luta where luta.campeonato = :campeonato");
            q.setParameter("campeonato", campeonato);
            lutas = q.getResultList();
            
        } catch (IllegalStateException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        }
                
        //TEMP
        ArrayList<Luta> arrayDeLutas = new ArrayList<Luta>(lutas);
        
        return arrayDeLutas;

    }
    
    @Override
    public void deleteLutaPorCampeonato(Campeonato campeonato){

        em.getTransaction().begin();
        Query q = em.createQuery("delete from Luta luta where luta.campeonato=:campeonato");
        q.setParameter("campeonato", campeonato);
        q.executeUpdate();
        em.getTransaction().commit();
        
    }
                
}
