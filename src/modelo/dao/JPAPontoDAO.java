package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import modelo.Luta;
import modelo.Ponto;
import modelo.dao.interfaces.PontoDAO;
import modelo.excecoes.DAOException;

/**
 *
 * @author Renato Pinheiro
 */
public class JPAPontoDAO implements PontoDAO{ 

    private EntityManager em;
    
    /** Creates a new instance of CampeonatoDAO */
    public JPAPontoDAO() {
        em = ConexaoJPA.getInstance().getEntityManager();
    }
    
    @Override
    public int save(Ponto ponto) throws DAOException {
        
        try {
            
            em.getTransaction().begin();

            em.persist(ponto);
            
            em.getTransaction().commit();
            
        } catch (EntityExistsException ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Lutador já cadastrado", ex.getCause());
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
        
        return ponto.getId();
        
    }
    
    @Override
    public void delete(Ponto ponto) throws DAOException  {
        
        try {
            
            em.getTransaction().begin();

            em.remove(ponto);
            
            em.getTransaction().commit();
            
        } catch (EntityExistsException ex) {
            em.getTransaction().rollback();
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Lutador já cadastrado", ex.getCause());
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
    public ArrayList listPontoPorLuta(Luta luta) throws DAOException {

        List<Ponto> pontos = null;
        
        try {
            
            Query q = em.createQuery("select ponto from Ponto ponto where ponto.luta = :luta");
            q.setParameter( "luta", luta);
            pontos = q.getResultList();
            
        } catch (IllegalStateException ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        } catch (IllegalArgumentException  ex) {
            util.Util.gravaExcecao(ex, this);
            throw new DAOException("Erro no banco de dados", ex.getCause());
        }
                
        return new ArrayList<Ponto>(pontos);

        
    }
            
}
